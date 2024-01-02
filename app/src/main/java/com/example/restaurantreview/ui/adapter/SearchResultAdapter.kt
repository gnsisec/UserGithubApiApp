package com.example.restaurantreview.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.databinding.ItemReviewBinding
import com.example.restaurantreview.ui.UserProfileActivity
import com.example.restaurantreview.viewmodel.ProfileViewModel

class SearchResultAdapter() :
    ListAdapter<ItemsItem, SearchResultAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userInfo: ItemsItem) {
            binding.tvItem.text = "${userInfo.login}".replaceFirstChar { it.uppercase() }
            Glide.with(this@ViewHolder.itemView.context).load("${userInfo.avatarUrl}")
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivItem)

            binding.itemReview.setOnClickListener {
                val intent = Intent((it.context), UserProfileActivity::class.java)
                intent.putExtra("username", "${userInfo.login}")
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
