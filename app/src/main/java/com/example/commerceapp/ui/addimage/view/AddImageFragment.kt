package com.example.commerceapp.ui.addimage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.commerceapp.R
import com.example.commerceapp.databinding.FragmentAddImageBinding
import com.example.commerceapp.databinding.FragmentProductsBinding


class AddImageFragment : Fragment() {
    private var _binding :FragmentAddImageBinding? = null
  private  val binding =  _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddImageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnAddImageVariuns?.setOnClickListener{}
    }

}