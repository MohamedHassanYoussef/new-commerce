package com.example.commerceapp.ui.home.view

import HomeViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.commerceapp.databinding.FragmentHomeBinding
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.home.viewmodel.HomeFactory

import kotlinx.coroutines.launch
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        HomeFactory(
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeProductCount()

        binding.cardViewProduct.setOnClickListener {
            navigateToProducts()
        }

        binding.cardViewCoupouns.setOnClickListener {
            navigateToCoupons()
        }
        observeProductCount()
    }

    private fun observeProductCount() {
        lifecycleScope.launch {
            homeViewModel.productCount.collect { state ->
                when (state) {
                    is State.Loading -> {  }
                    is State.Success -> {}//binding.textViewCountProduct.text = state.data.body()?.count.toString()
                    is State.Error -> Log.e("HomeFragment", "Error fetching product count: ${state.message}")
                }
            }
        }
    }



    private fun navigateToProducts() {
        val action = HomeFragmentDirections.actionNavigationHomeToProducts()
        findNavController().navigate(action)
    }

    private fun navigateToCoupons() {
        val actionCoupon = HomeFragmentDirections.actionNavigationHomeToCoupouns()
        findNavController().navigate(actionCoupon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
