package com.example.commerceapp.ui.editcoupon.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.commerceapp.databinding.FragmentEditCouponBinding
import com.example.commerceapp.model.RepositoryImplementation
import com.example.commerceapp.network.RemoteImplementation
import com.example.commerceapp.network.RetrofitHelper
import com.example.commerceapp.ui.editcoupon.viewmodel.EditCouponFactory
import com.example.commerceapp.ui.editcoupon.viewmodel.EditCouponViewModel


class EditCouponFragment : Fragment() {

    private var _binding: FragmentEditCouponBinding? = null
    private val binding get() = _binding!!

    private val editCouponViewmodel: EditCouponViewModel by viewModels {
        EditCouponFactory(
            RepositoryImplementation.getInstance(
                RemoteImplementation.getInstance(RetrofitHelper.retrofit)
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCouponBinding.inflate(inflater, container, false)
        return binding.root

    }


}