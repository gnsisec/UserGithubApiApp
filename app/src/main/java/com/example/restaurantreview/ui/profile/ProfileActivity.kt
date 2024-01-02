package com.example.restaurantreview.ui.profile

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.R
import com.example.restaurantreview.data.response.GithubUserProfile
import com.example.restaurantreview.databinding.ActivityProfileBinding
import com.example.restaurantreview.ui.follow.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent?.getStringExtra("username").toString()
        val profileViewModelFactory = ProfileViewModelFactory(username)
        val profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        with(profileViewModel) {
            userProfile.observe(this@ProfileActivity) {
                showUserProfile(it)
            }
            isLoading.observe(this@ProfileActivity) {
                showLoading(it)
            }
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
        binding.tvUsername.text = profile.login
        binding.tvDisplayName.text = profile.name
        Glide.with(this@ProfileActivity).load(profile.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivProfile)
        binding.followerStat.text = getString(R.string.follower_stat, profile.followers)
        binding.followingStat.text = getString(R.string.following_stat, profile.following)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}