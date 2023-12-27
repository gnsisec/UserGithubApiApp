package com.example.restaurantreview.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityDetailUserBinding
import com.example.restaurantreview.viewmodel.ProfileViewModel

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileViewModel.username.observe(this) {
            updateUsername(it)
        }

        profileViewModel.displayName.observe(this) {
            updateDisplayName(it)
        }

        profileViewModel.avatar.observe(this) {
            updateAvatar(it)
        }

        profileViewModel.followers.observe(this) {
            updateFollowerStats(it)
        }

        profileViewModel.followings.observe(this) {
            updateFollowingStats(it)
        }

        // TODO: This should be DRY. Duplicate with MainActivity
        //  Create a helper / util for this
        profileViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val username = intent.getStringExtra("username").toString()
        profileViewModel.showProfile(username)
    }

    private fun updateUsername(username: String) {
        binding.tvUsername.text = username
    }

    private fun updateDisplayName(displayName: String) {
        binding.tvDisplayName.text = displayName
    }

    private fun updateAvatar(avatarUrl: String) {
        Glide.with(this@DetailUser).load(avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivProfile)
    }

    private fun updateFollowerStats(follower: Int) {
        binding.followerStat.text = getString(R.string.follower_stat, follower)
    }

    private fun updateFollowingStats(following: Int) {
        binding.followingStat.text = getString(R.string.following_stat, following)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}