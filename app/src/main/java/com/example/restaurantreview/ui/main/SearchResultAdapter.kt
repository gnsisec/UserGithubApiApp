package com.example.restaurantreview.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.data.response.UserAttributes
import com.example.restaurantreview.databinding.UsersListBinding
import com.example.restaurantreview.ui.profile.ProfileActivity

class SearchResultAdapter :
    ListAdapter<UserAttributes, SearchResultAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: UsersListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userInfo: UserAttributes) {
            binding.tvItem.text = "${userInfo.login}"
            Glide.with(this@ViewHolder.itemView.context).load("${userInfo.avatarUrl}")
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivItem)

            binding.itemReview.setOnClickListener {
                val intent = Intent((it.context), ProfileActivity::class.java)
                intent.putExtra("username", "${userInfo.login}")
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UsersListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserAttributes>() {
            override fun areItemsTheSame(
                oldItem: UserAttributes,
                newItem: UserAttributes,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserAttributes,
                newItem: UserAttributes,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
