package com.example.restaurantreview.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.R
import com.example.restaurantreview.data.response.GithubUserProfile
import com.example.restaurantreview.databinding.ActivityDetailUserBinding
import com.example.restaurantreview.util.SectionsPagerAdapter
import com.example.restaurantreview.viewmodel.ProfileViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    companion object {
        var username: String = ""
        const val TAG = "UserProfileActivity"

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

        username = intent.getStringExtra("username").toString()

        with(profileViewModel) {
            userProfile.observe(this@UserProfileActivity) {
                showUserProfile(it)
            }
            isLoading.observe(this@UserProfileActivity) {
                showLoading(it)
            }
            getProfile(username)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showUserProfile(profile: GithubUserProfile) {
        // TODO: remove log nya sebelum submit
        Log.d(TAG, "showUserProfile ke panggil lagi ni")
        binding.tvUsername.text = profile.login
        binding.tvDisplayName.text = profile.name
        Glide.with(this@UserProfileActivity).load(profile.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivProfile)
        binding.followerStat.text = getString(R.string.follower_stat, profile.followers)
        binding.followingStat.text = getString(R.string.following_stat, profile.following)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}