package com.example.commerceapp.ui.addproduct.view

import android.app.AlertDialog


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.commerceapp.databinding.FragmentProductDetailsBinding
import com.example.commerceapp.model.AddProduct
import com.example.commerceapp.model.ImageAdd
import com.example.commerceapp.model.Product
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.model.VariantAddProduct
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.addproduct.viewmodel.ProductDetailsFactory
import com.example.commerceapp.ui.addproduct.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    var image: ImageAdd? = null

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

        val title = binding.etTitle.text.toString().trim()
        val brand = binding.etBrand.text.toString().trim()
        val priceInput = binding.etPrice.text.toString().trim()
        val price = priceInput.toDoubleOrNull()
        val imageUrl = binding.etImageUrl.toString().trim()
        val productType = binding.etProductType.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()


        if (priceInput.isEmpty() || price == null || price <= 0) {
            showToast("A valid price greater than zero is required")
            return
        }


        if (imageUrl.isEmpty()) {
            showToast("Image url is required")
            return
        }

        if (title.isEmpty()) {
            showToast("Title is required")
            return
        }
        if (brand.isEmpty()) {
            showToast("Brand is required")
            return
        }
        if (price == null || price <= 0) {
            showToast("Valid price is required")
            return
        }

        if (productType.isEmpty()) {
            showToast("Product type is required")
            return
        }
        if (description.isEmpty()) {
            showToast("Description is required")
            return
        }



        val newProduct = AddProduct(
            product = Product(
                title = title,
                bodyHTML = description,
                vendor = brand,
                productType = productType,
                tags = "tag1, tag2",
                variants = listOf(
                    VariantAddProduct(
                        option1 = "Default",
                        price = price.toString(),
                        sku = "SKU12345"
                    )
                ),
                images = listOf(image)
            )
        )

        /*   val newProduct = AddProduct(
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
    )*/



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


        val imageUrl = binding.etImageUrl.text.toString().trim()
        image = ImageAdd(imageUrl, imageUrl)
        if (imageUrl.isNotEmpty()) {
            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .into(binding.imageView)

            Log.d("openImagePicker", "Loading image from: $imageUrl")
        } else {

            Toast.makeText(requireContext(), "Please enter a valid image URL", Toast.LENGTH_SHORT)
                .show()
            Log.d("openImagePicker", "Image URL is empty")
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
