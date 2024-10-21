package com.example.commerceapp.ui.coupouns.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.commerceapp.databinding.CouponsPriceRuleItemBinding
import com.example.commerceapp.model.Currency
import com.example.commerceapp.model.PriceRule
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdapterCoupon(
    private val onItemClick: (PriceRule) -> Unit,
    private val onDeleteClick: (PriceRule) -> Unit
) : ListAdapter<PriceRule, ViewHolderCoupon>(CouponDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCoupon {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = CouponsPriceRuleItemBinding.inflate(inflater, parent, false)
        return ViewHolderCoupon(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderCoupon, position: Int) {
        val coupon = getItem(position)


        holder.binding.tvCoponName.text = coupon.title
        holder.binding.tvFromTime.text = formatDate(coupon.starts_at)

        val price = coupon.value
        val currency = Currency.EGP
        holder.binding.tvCoponValue.text = "$price ${currency.symbol} "


        Log.d("eeee2222", "CouponViewModel: ${coupon.id}")

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClick(coupon)
        }

        holder.itemView.setOnClickListener {
            onItemClick(coupon)
        }
    }

    private fun formatDate(dateString: String): String {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val date: Date? = inputFormat.parse(dateString)


        val outputFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        return date?.let { outputFormat.format(it) } ?: ""
    }
}

class ViewHolderCoupon(val binding: CouponsPriceRuleItemBinding) : RecyclerView.ViewHolder(binding.root)

class CouponDiffUtil : DiffUtil.ItemCallback<PriceRule>() {
    override fun areItemsTheSame(oldItem: PriceRule, newItem: PriceRule): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: PriceRule, newItem: PriceRule): Boolean {
        return oldItem == newItem
    }
}
