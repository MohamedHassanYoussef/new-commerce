package com.example.commerceapp.ui.editProduct.view

import ImageEdit
import Product
import UpdateProduct
import Variant22
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.commerceapp.R
import com.example.commerceapp.databinding.FragmentEditProductBinding
import com.example.commerceapp.model.ImageAdd
import com.example.commerceapp.model.Products
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.editProduct.viewmodel.EditProductFactory
import com.example.commerceapp.ui.editProduct.viewmodel.EditProductViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProductFragment : Fragment() {
    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var product: Products

    var image: ImageEdit? = null


    private val brands = listOf(
        "VANS", "TIMBERLAND", "SUPRA", "PUMA", "PALLADIUM",
        "NIKE", "HERSCHEL", "FLEX FIT", "DR MARTENS",
        "CONVERSE", "Burton", "ASICS TIGER", "ADIDAS"
    )
    private val productTypes = arrayOf("SHOES", "ACCESSORIES", "T-SHIRTS", "SNOWBOARD")


    private val editProductViewmodel: EditProductViewmodel by viewModels {
        EditProductFactory(
            RepositoryImplementation.getInstance(
                RemoteImplementation.getInstance(RetrofitHelper.retrofit)
            )
        )
    }

    private val args: EditProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receivedProductId = args.editproduct

       binding.btnAddImageProduct.setOnClickListener { openImagePicker() }
        binding.etProductTypeProduct.setOnClickListener{showProductTypeSelectionDialog()}
        binding.etBrandProduct.setOnClickListener{showBrandSelectionDialog()}


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            editProductViewmodel.editProduct.collect { state ->
                when (state) {
                    is State.Success -> {
                        product = state.data.products.firstOrNull { it.id == receivedProductId }
                            ?: return@collect

                        binding.etBrandProduct.text = Editable.Factory.getInstance()
                            .newEditable(product.vendor ?: "Brand not available")
                        binding.etTitleProduct.text = Editable.Factory.getInstance()
                            .newEditable(product.title ?: "Title not available")
                        binding.etDescriptionProduct.text = Editable.Factory.getInstance()
                            .newEditable(product.bodyHtml ?: "Description not available")

                        binding.etImageUrl.text = Editable.Factory.getInstance()
                            .newEditable(product.image?.src ?: "No image URL available")

                        binding.etProductTypeProduct.text = Editable.Factory.getInstance()
                            .newEditable(product.productType ?: "Product type not available")

                        binding.etPriceProduct.text = Editable.Factory.getInstance()
                            .newEditable(product.variants.firstOrNull()?.price ?: "Price not available")

                        product.image?.let {
                            Glide.with(requireContext())
                                .load(it.src)
                                .into(binding.ivInsert)
                        } ?: run {
                            binding.ivInsert.setImageResource(R.drawable.one)
                        }
                    }

                    is State.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Make sure you are connected to the internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is State.Loading -> {

                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            editProductViewmodel.productUpdateState.collect { state ->
                when (state) {
                    is State.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Product updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()


                        val reLoadProduct :Boolean = true

                        val action = EditProductFragmentDirections.actionEditProductFragmentToProducts()
                        action.setReLoadProduct(reLoadProduct)
                        findNavController().navigate(action)




                    }
                    is State.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error updating product",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is State.Loading -> {

                    }
                }
            }
        }

        binding.btnEditProduct.setOnClickListener {
            val priceInput = binding.etPriceProduct.text.toString()
            val price = priceInput.toDoubleOrNull()
            if (price == null || price <= 0) {
                Toast.makeText(requireContext(), "Please enter a price greater than zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val imageUrl2 = binding.etImageUrl.text.toString().trim()
            if (imageUrl2.isBlank()) {
                Toast.makeText(requireContext(), "Please enter a valid image URL", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                openImagePicker()
            }

            val updatedProduct = Product(
                id = product.id,
                title = binding.etTitleProduct.text.toString(),
                bodyHTML = binding.etDescriptionProduct.text.toString(),
                vendor = binding.etBrandProduct.text.toString(),
                productType = binding.etProductTypeProduct.text.toString(),
                variants = listOf(
                    Variant22(
                        price = priceInput
                        //  option2 = binding.etColorProduct.text.toString()
                    )
                ),
                images = listOf(image)
            )

            val updateProduct = UpdateProduct(product = updatedProduct)
            Log.d("EditProductFragment", "Attempting to update product: $updateProduct")

            editProductViewmodel.updateProductDetails(updateProduct)
        }
    }


    private fun openImagePicker() {
        Log.d("openImagePicker", "openImagePicker called")
        val imageUrl = binding.etImageUrl.text.toString().trim()
        image = ImageEdit(imageUrl, imageUrl)
        if (imageUrl.isNotEmpty()) {
            Glide.with(binding.ivInsert.context)
                .load(imageUrl)
                .into(binding.ivInsert)
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
            .setItems(brands.toTypedArray()) { _, which -> binding.etBrandProduct.setText(brands[which]) }
            .show()
    }

    private fun showProductTypeSelectionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Product Type")
            .setItems(productTypes) { _, which -> binding.etProductTypeProduct.setText(productTypes[which]) }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}