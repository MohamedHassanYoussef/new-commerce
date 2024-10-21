package com.example.commerceapp.ui.coupouns.view

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commerceapp.R
import com.example.commerceapp.databinding.FragmentCoupounsBinding
import com.example.commerceapp.model.PriceRule
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.coupouns.viewmodel.CouponViewModel
import com.example.commerceapp.ui.coupouns.viewmodel.CouponsFactory
import com.example.commerceapp.ui.productedit.view.ProductDetailsArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoupounsFragment : Fragment() {

    private var _binding: FragmentCoupounsBinding? = null
    private val binding get() = _binding!!

    private val args: CoupounsFragmentArgs by navArgs()
    private  var reLoadData :Boolean? =null

    private lateinit var adapterCoupon: AdapterCoupon

    private val couponViewModel: CouponViewModel by activityViewModels {
        CouponsFactory(
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
        _binding = FragmentCoupounsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fsbNewCoupons.setOnClickListener {
            findNavController().navigate(R.id.action_coupouns_to_addCouponFragment)
        }

        adapterCoupon = AdapterCoupon(
            onDeleteClick = { coupon -> showDeleteConfirmationDialog(coupon) },
            onItemClick = { coupon -> }
        )

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterCoupon
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


         reLoadData = args.addCoupon
        if (reLoadData as Boolean && reLoadData == true) {
            couponViewModel.getAllCoupon()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            couponViewModel.coupon.collect { state ->
                when (state) {
                    is State.Success -> {
                        adapterCoupon.submitList(state.data.priceRules)
                        Log.d("eeee2222", "CoupounsFragment: ${state.data.priceRules}")
                    }

                    is State.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Make sure you are connected to the internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is State.Loading -> {}
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            couponViewModel.coupon.collect { state ->
                when (state) {
                    is State.Success -> {
                        adapterCoupon.submitList(state.data.priceRules)

                    }

                    is State.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Make sure you are connected to the internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is State.Loading -> {}
                }
            }
        }


    }


    private fun showDeleteConfirmationDialog(coupon: PriceRule) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm Deletion")
        builder.setMessage("Are you sure you want to delete this coupon?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            Log.d("deleteCoupon", "no crash: ")
            val priceRuleId = coupon.id
            Log.d("deleteCoupon", "showDeleteConfirmationDialogAfter:$priceRuleId ")
            couponViewModel.deleteCoupon(priceRuleId)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
