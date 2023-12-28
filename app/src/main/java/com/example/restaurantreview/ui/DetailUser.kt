package com.example.restaurantreview.ui

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityDetailUserBinding
import com.example.restaurantreview.util.SectionsPagerAdapter
import com.example.restaurantreview.viewmodel.ProfileViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val profileViewModel by viewModels<ProfileViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

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