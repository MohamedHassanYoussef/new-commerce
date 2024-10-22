package com.example.commerceapp.ui.addcoupon.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.commerceapp.R
import com.example.commerceapp.databinding.FragmentAddCouponBinding
import com.example.commerceapp.model.AddCoupon
import com.example.commerceapp.model.PrerequisiteToEntitlementPurchase
import com.example.commerceapp.model.PrerequisiteToEntitlementQuantityRatio
import com.example.commerceapp.model.PrerequisiteToEntitlementQuantityRatio1
import com.example.commerceapp.model.PriceRule
import com.example.commerceapp.model.PriceRule1
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.network.State
import com.example.commerceapp.ui.addcoupon.viewmodel.AddCouponFactory
import com.example.commerceapp.ui.addcoupon.viewmodel.AddCouponViewModel
import com.example.commerceapp.ui.products.view.ProductFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.random.Random

class AddCouponFragment : Fragment() {

    private var _binding: FragmentAddCouponBinding? = null
    private val binding get() = _binding!!

    private val addCouponViewModel: AddCouponViewModel by viewModels {
        AddCouponFactory(
            RepositoryImplementation.getInstance(
                RemoteImplementation.getInstance(RetrofitHelper.retrofit)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCouponBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.btnAddCoupon.setOnClickListener {
                val couponCode = binding.etCouponTitle.text.toString()
                var couponValue = binding.etValue.text.toString()
                val couponStart = getCurrentTime()

                if (couponCode.isBlank()) {
                    binding.etCouponTitle.error = "Title is required"
                    return@setOnClickListener
                }
                if (couponValue.isBlank()) {
                    binding.etValue.error = "Value is required"
                    return@setOnClickListener
                }

                val couponValueAsNumber = couponValue.toDoubleOrNull()
                if (couponValueAsNumber != null && couponValueAsNumber > 0) {
                    couponValue = (-couponValueAsNumber).toString()
                }




                val newCoupon = AddCoupon(
                    priceRule = PriceRule1(
                        title = couponCode,
                        valueType = "percentage",
                        value = couponValue,
                        customerSelection = "all",
                        targetType = "line_item",
                        targetSelection = "entitled",
                        allocationMethod = "each",
                        startsAt = couponStart,
                        prerequisiteCollectionIDS = listOf(480515064112),
                        entitledProductIDS = listOf(9623292215600),
                        prerequisiteToEntitlementQuantityRatio = PrerequisiteToEntitlementQuantityRatio1(
                            prerequisiteQuantity = 1,
                            entitledQuantity = 1
                        ),
                        allocationLimit = 10
                    )
                )

                addCouponViewModel.addCoupon(newCoupon)
            }


            lifecycleScope.launch{
                addCouponViewModel.addCouponState.collect { state ->
                    when (state) {
                        is State.Success -> {
                            if (state.data) {
                                Toast.makeText(
                                    requireContext(),
                                    "Coupon added successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val reLoadData :Boolean = true

                                val action = AddCouponFragmentDirections.actionAddCouponFragmentToCoupouns()
                                action.setAddCoupon(reLoadData)
                                findNavController().navigate(action)

                            }
                        }

                        is State.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Error: ${state.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is State.Loading -> {

                        }
                    }
                }
            }
        }



        private fun getCurrentTime(): String {
        val currentTime = System.currentTimeMillis()
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")
        return outputFormat.format(currentTime)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




/*        val coupon = PriceRule(
            id = 1467264008495,
            value_type = "percentage",
            value = "-45.5",
            customer_selection = "all",
            target_type = "line_item",
            target_selection = "all",
            allocation_method = "across",
            allocation_limit= null,
            once_per_customer = false,
            usage_limit = null,
            starts_at = "2024-10-21T11:38:03-04:00",
            ends_at = null,
            created_at = "2024-10-21T11:38:17-04:00",
            updatedAt = "2024-10-21T11:38:17-04:00",
            entitledProductIDS = emptyList(),
            entitledVariantIDS = emptyList(),
            entitledCollectionIDS = emptyList(),
            entitledCountryIDS = emptyList(),
            prerequisiteProductIDS = emptyList(),
            prerequisiteVariantIDS = emptyList(),
            prerequisiteCollectionIDS = emptyList(),
            customerSegmentPrerequisiteIDS = emptyList(),
            prerequisiteCustomerIDS = emptyList(),
            prerequisiteSubtotalRange = null,
            prerequisiteQuantityRange = null,
            prerequisiteShippingPriceRange = null,
            prerequisiteToEntitlementQuantityRatio = PrerequisiteToEntitlementQuantityRatio(
                prerequisiteQuantity =null,
                entitledQuantity = null
            ),
            prerequisiteToEntitlementPurchase = PrerequisiteToEntitlementPurchase(prerequisiteAmount = null),
            title = "h6vgfdrf",
            adminGraphqlAPIID = "gid://shopify/PriceRule/1467264008495"
        )*/

/* val coupon = PriceRule(
     id = couponId,
     value_type = "percentage",
     value = couponValue,
     customer_selection = "all",
     target_type = "line_item",
     target_selection = "all",
     allocation_method = "across",
     allocation_limit = null,
     once_per_customer = false,
     usage_limit = null,
     starts_at = couponStart,
     ends_at = couponEnd,
     created_at = getCurrentTime(),
     updatedAt = getCurrentTime(),
     entitledProductIDS = emptyList(),
     entitledVariantIDS = emptyList(),
     entitledCollectionIDS = emptyList(),
     entitledCountryIDS = emptyList(),
     prerequisiteProductIDS = emptyList(),
     prerequisiteVariantIDS = emptyList(),
     prerequisiteCollectionIDS = emptyList(),
     customerSegmentPrerequisiteIDS = emptyList(),
     prerequisiteCustomerIDS = emptyList(),
     prerequisiteSubtotalRange = null,
     prerequisiteQuantityRange = null,
     prerequisiteShippingPriceRange = null,
     prerequisiteToEntitlementQuantityRatio = PrerequisiteToEntitlementQuantityRatio(
         prerequisiteQuantity = null,
         entitledQuantity = null
     ),
     prerequisiteToEntitlementPurchase = PrerequisiteToEntitlementPurchase(prerequisiteAmount = null),
     title = couponCode,
     adminGraphqlAPIID = "gid://shopify/PriceRule/$couponId"
 )*/