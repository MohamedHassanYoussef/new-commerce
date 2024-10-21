package com.example.commerceapp.ui.productedit.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.commerceapp.R
import com.example.commerceapp.databinding.FragmentProductEditeBinding
import com.example.commerceapp.model.Currency
import com.example.commerceapp.model.Products
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.products.viewmodel.ProductFactory
import com.example.commerceapp.ui.products.viewmodel.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetails : Fragment() {

    private var _binding: FragmentProductEditeBinding? = null
    private val binding get() = _binding!!

    private val args: ProductDetailsArgs by navArgs()
    lateinit var product: Products
    private var productId: Long? = null

    private val ProductViewModel: ProductViewModel by activityViewModels {
        ProductFactory(
            RepositoryImplementation.getInstance(
                RemoteImplementation.getInstance(RetrofitHelper.retrofit)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductEditeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productId = args.productId
        Log.d("TAG222", "ProductEdit: $productId")

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            ProductViewModel.productData.collect { state ->
                when (state) {
                    is State.Success -> {
                        product = state.data.products.filter { it.id == productId }.first()
                        binding.textBrandName.text = product.vendor ?: "Vendor not available"
                        binding.textViewProductName.text = product.title
                        binding.textProductDetailDesc.text = product.bodyHtml
                     //   binding.textColorTy.text = product.variants[0].option2
                        binding.tvProductType.text = product.productType

                        if (product.variants != null && product.variants.isNotEmpty()) {
                            val price = product.variants[0].price
                            val currency = Currency.EGP

                            binding.textViewProductPrice.text = "$price ${currency.symbol} "
                        } else {
                            binding.textViewProductPrice.text = "Price not available"
                        }

                        if (product.image != null) {
                            Glide.with(requireContext())
                                .load(product.image!!.src)
                                .into(binding.imageViewProductEdit)
                        } else {
                            binding.imageViewProductEdit.setImageResource(R.drawable.one)
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

        binding.btnEditeProduct.setOnClickListener {
            val action = ProductDetailsDirections.actionProductEditToEditProductFragment(productId ?: 0L)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
