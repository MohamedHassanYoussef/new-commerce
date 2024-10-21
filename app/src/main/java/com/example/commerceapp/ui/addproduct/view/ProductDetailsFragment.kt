package com.example.commerceapp.ui.addproduct.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.commerceapp.R
import com.example.commerceapp.databinding.FragmentProductDetailsBinding
import com.example.commerceapp.model.AddProduct
import com.example.commerceapp.model.ImageAdd
import com.example.commerceapp.model.Product
import com.example.commerceapp.model.ProductElement1
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.model.VariantAddProduct
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.addproduct.viewmodel.ProductDetailsFactory
import com.example.commerceapp.ui.addproduct.viewmodel.ProductDetailsViewModel
import com.example.commerceapp.ui.editProduct.view.EditProductFragmentDirections
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001

    var image : ImageAdd? = null

    private val brands = listOf(
        "VANS", "TIMBERLAND", "SUPRA", "PUMA", "PALLADIUM",
        "NIKE", "HERSCHEL", "FLEX FIT", "DR MARTENS",
        "CONVERSE", "Burton", "ASICS TIGER", "ADIDAS"
    )
    private val productTypes = arrayOf("SHOES", "ACCESSORIES", "T-SHIRTS", "SNOWBOARD")

    private val productDetailsViewModel: ProductDetailsViewModel by viewModels {
        ProductDetailsFactory(
            RepositoryImplementation.getInstance(
                RemoteImplementation.getInstance(RetrofitHelper.retrofit)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        setupUIComponents()
        observeViewModel()
        return binding.root
    }

    private fun setupUIComponents() {
        with(binding) {
            etProductType.setOnClickListener { showProductTypeSelectionDialog() }
            etBrand.setOnClickListener { showBrandSelectionDialog() }
            btnAddImage.setOnClickListener { openImagePicker() }
            btnAddProduct.setOnClickListener { addProduct() }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            productDetailsViewModel.productAdditionState.collect { state ->
                when (state) {
                    is State.Loading -> Log.d("ProductDetailsFragment", "Loading...")
                    is State.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Product added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearInputFields()
                    }

                    is State.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun addProduct() {
        /*       val thumbnail = selectedImageUri?.toString() ?: ""
        val title = binding.etTitle.text.toString()
        val brand = binding.etBrand.text.toString()
        val price = binding.etPrice.text.toString().toDoubleOrNull() ?: 0.0
        val color = binding.etColor.text.toString()
        val productType = binding.etProductType.text.toString()
        val description = binding.etDescription.text.toString()

        if (title.isEmpty()) {
            showToast("Title is required")
            return
        }
        if (brand.isEmpty()) {
            showToast("Brand is required")
            return
        }
        if (price <= 0) {
            showToast("Valid price is required")
            return
        }
        if (color.isEmpty()) {
            showToast("Color is required")
            return
        }
        if (productType.isEmpty()) {
            showToast("Product type is required")
            return
        }
        if (description.isEmpty()) {
            showToast("Description is required")
            return
        }*/

        /* val productElement = ProductElement1(
            thm = "https://cdn.shopify.com/s/files/1/0895/0179/4608/files/e7a2b189514d134630552681e4c8bc07.jpg?v=1728735477",
            title = title,
            brand = brand,
            price = price,
            color = color,
            type = productType,
            description = description
        )*/

        val newProduct = AddProduct(
            product = Product(
                title = binding.etTitle.text.toString(),
                bodyHTML = binding.etDescription.text.toString(),
                vendor = binding.etBrand.text.toString(),
                productType = binding.etProductType.text.toString(),
                tags = "tag1, tag2",
                variants = listOf(
                    VariantAddProduct(
                        option1 = "Default",
                        price = binding.etPrice.text.toString(),
                        sku = "SKU12345"
                    )
                ),
                images = listOf(image)
            )
        )



        Log.d("addProduct", "addProduct:$newProduct ")
        productDetailsViewModel.addProduct(newProduct)

        viewLifecycleOwner.lifecycleScope.launch {
            productDetailsViewModel.productAdditionState.collect { state ->
                when (state) {
                    is State.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Product added successfully",
                            Toast.LENGTH_SHORT
                        ).show()


                        val reLoadProduct: Boolean = true

                        val action =
                            ProductDetailsFragmentDirections.actionProductDetailsFragmentToProducts()
                        action.setReLoadProduct(reLoadProduct)
                        findNavController().navigate(action)


                    }

                    is State.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error updating product: ${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is State.Loading -> {

                    }
                }
            }


        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }






    private fun openImagePicker() {
        Log.d("openImagePicker", "openImagePicker called")

        // Retrieve the image URL from the EditText and trim extra spaces
        val imageUrl = binding.etImageUrl.text.toString().trim()
        image = ImageAdd(imageUrl, imageUrl)
        if (imageUrl.isNotEmpty()) {
            // Load the image using Glide with a placeholder and error image
            Glide.with(binding.imageView.context)
                .load(imageUrl)

                .into(binding.imageView)

            Log.d("openImagePicker", "Loading image from: $imageUrl")
        } else {
            // Handle case where the URL is empty
            Toast.makeText(requireContext(), "Please enter a valid image URL", Toast.LENGTH_SHORT).show()
            Log.d("openImagePicker", "Image URL is empty")
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data!!
            binding.imageView.setImageURI(selectedImageUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImageFromGallery()
        } else {
            showToast("Permission denied")
        }
    }

    private fun showBrandSelectionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Brand")
            .setItems(brands.toTypedArray()) { _, which -> binding.etBrand.setText(brands[which]) }
            .show()
    }

    private fun showProductTypeSelectionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Product Type")
            .setItems(productTypes) { _, which -> binding.etProductType.setText(productTypes[which]) }
            .show()
    }

    private fun clearInputFields() {
        binding.apply {
            etTitle.text?.clear()
            etBrand.text?.clear()
            etPrice.text?.clear()
            etImageUrl.text?.clear()
            etProductType.text?.clear()
            etDescription.text?.clear()
            imageView.setImageURI(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_DENIED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                PERMISSION_CODE
//            )
//        } else {
//            pickImageFromGallery()
//        }