package com.example.commerceapp.ui.addimage.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.commerceapp.R
import com.example.commerceapp.databinding.ImageItemBinding
import com.example.commerceapp.model.Products


class AddImageAdapter(private val onDeleteClick: (Products) -> Unit) :
    ListAdapter<Products, ViewHolderAddImage>(ProductDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAddImage {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)
        return ViewHolderAddImage(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderAddImage, position: Int) {
        val image = getItem(position)
        if (image.image != null) {
            Glide.with(holder.itemView.context)
                .load(image.image.src)
                .into(holder.binding.ivProductVariant)
        } else {

            holder.binding.ivProductVariant.setImageResource(R.drawable.one)
        }


        holder.binding.ivCancel.setOnClickListener {
            onDeleteClick(image)
        }
    }
}


class ViewHolderAddImage(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)

class ProductDiffUtil : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem
    }
}