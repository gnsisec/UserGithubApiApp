package com.example.restaurantreview.ui

import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.data.response.GithubUserProfile
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.databinding.ItemReviewBinding
import kotlinx.coroutines.withContext

class SearchResultAdapter : ListAdapter<ItemsItem, SearchResultAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userInfo: ItemsItem) {
            binding.tvItem.text = "${userInfo.login}".replaceFirstChar { it.uppercase() }
            Glide.with(this@ViewHolder.itemView.context).load("${userInfo.avatarUrl}")
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivItem)
            binding.itemReview.setOnClickListener {
                it.context.startActivity(Intent(it.context, DetailUser::class.java))
                // TODO: Add Detail Profile Listener
                // Log.d("SearchResultAdapter", "click on ${userInfo.login}")
                // Toast.makeText(this@ViewHolder.itemView.context, "click on ${userInfo.login}", Toast.LENGTH_SHORT).show()
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
