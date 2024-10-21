package com.example.commerceapp.ui.products.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.commerceapp.R
import com.example.commerceapp.databinding.FragmentProductsBinding
import com.example.commerceapp.model.PriceRule
import com.example.commerceapp.model.Products
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.coupouns.view.CoupounsFragmentArgs
import com.example.commerceapp.ui.products.viewmodel.ProductFactory
import com.example.commerceapp.ui.products.viewmodel.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: AdapterProduct

    private val args: ProductFragmentArgs by navArgs()
    private  var reLoadDataProduct :Boolean? =null

    private val ProductViewModel: ProductViewModel by activityViewModels {
        ProductFactory(
            RepositoryImplementation.getInstance(
                RemoteImplementation.getInstance(RetrofitHelper.retrofit)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.fsbNewProduct.setOnClickListener {
            findNavController().navigate(R.id.action_products_to_productDetailsFragment)
        }


        productAdapter = AdapterProduct(
            onItemClick = { product ->
                val action = ProductFragmentDirections.actionProductsToProductEdit(product.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { product -> showDeleteConfirmationDialog(product) }
        )
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reLoadDataProduct = args.reLoadProduct
        if (reLoadDataProduct as Boolean && reLoadDataProduct == true) {
            ProductViewModel.getProducts()
        }



        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            ProductViewModel.productData.collect { state ->
                when (state) {
                    is State.Success -> {
                        productAdapter.submitList(state.data.products)
                    }

                    is State.Error -> {

                        Toast.makeText(
                            requireContext(),
                            " Make sure you are connected to the internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is State.Loading -> {

                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            ProductViewModel.productData.collect { state ->
                when (state) {
                    is State.Success -> {
                        productAdapter.submitList(state.data.products)
                    }

                    is State.Error -> {

                        Toast.makeText(
                            requireContext(),
                            " Make sure you are connected to the internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is State.Loading -> {

                    }
                }
            }
        }
    }



    private fun showDeleteConfirmationDialog(product: Products) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm Deletion")
        builder.setMessage("Are you sure you want to delete this product?")
        builder.setPositiveButton("Yes") { dialog, _ ->

            val productId = product.id
            Log.d("ProductFragment", "showDeleteConfirmationDialog:$productId ")
            ProductViewModel.deleteProduct(productId)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}