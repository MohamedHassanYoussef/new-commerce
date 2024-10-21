package com.example.commerceapp.ui.products.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.commerceapp.R
import com.example.commerceapp.databinding.ProductItemBinding
import com.example.commerceapp.model.Currency
import com.example.commerceapp.model.PriceRule
import com.example.commerceapp.model.Products

class AdapterProduct(private val onItemClick: (Products) -> Unit,
                     private val onDeleteClick: (Products) -> Unit) :
    ListAdapter<Products, ViewHolderProduct>(ProductDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProduct {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ViewHolderProduct(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderProduct, position: Int) {
        val product = getItem(position)
        holder.binding.textViewVendor.text = product.vendor
        val price = product.variants[0].price
        val currency = Currency.EGP


        holder.binding.textViewPrice.text = "$price ${currency.symbol} "

        if (product.image != null) {
            Glide.with(holder.itemView.context)
                .load(product.image.src)
                .into(holder.binding.imageView2)
        } else {

            holder.binding.imageView2.setImageResource(R.drawable.one)
        }

        holder.binding.cvItem.setOnClickListener {
            onItemClick(product)
        }
        holder.binding.ivDelete.setOnClickListener{
            onDeleteClick (product)
        }

    }


}

class ViewHolderProduct(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

class ProductDiffUtil : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem
    }
}