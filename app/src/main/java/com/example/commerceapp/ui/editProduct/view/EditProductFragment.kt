package com.example.commerceapp.ui.editProduct.view

import Product
import UpdateProduct
import Variant22
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
import com.example.commerceapp.model.Products
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.addcoupon.view.AddCouponFragmentDirections
import com.example.commerceapp.ui.editProduct.viewmodel.EditProductFactory
import com.example.commerceapp.ui.editProduct.viewmodel.EditProductViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProductFragment : Fragment() {
    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var product: Products

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
                        /*binding.etColorProduct.text = Editable.Factory.getInstance()
                            .newEditable(product.variants[0].option2?: "Color not available")*/
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
                            "Error updating product: ${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is State.Loading -> {

                    }
                }
            }
        }

        binding.btnEditProduct.setOnClickListener {
            val updatedProduct = Product(
                id = product.id,
                title = binding.etTitleProduct.text.toString(),
                bodyHTML = binding.etDescriptionProduct.text.toString(),
                vendor = binding.etBrandProduct.text.toString(),
                productType = binding.etProductTypeProduct.text.toString(),
                variants = listOf(
                    Variant22(
                        price = binding.etPriceProduct.text.toString()
                      //  option2 = binding.etColorProduct.text.toString()
                    )
                )
            )

            val updateProduct = UpdateProduct(product = updatedProduct)
            Log.d("EditProductFragment", "Attempting to update product: $updateProduct")


            editProductViewmodel.updateProductDetails(updateProduct)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
