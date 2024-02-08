package com.example.restaurantreview.ui.profile

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restaurantreview.R
import com.example.restaurantreview.data.remote.response.GithubUserProfile
import com.example.restaurantreview.databinding.ActivityProfileBinding
import com.example.restaurantreview.ui.follow.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var username: String
    private lateinit var avatarUrl: String
    private val bookmarkUserViewModel: BookmarkUserViewModel by viewModels {
        BookmarkUserViewModelFactory.getInstance(
            application
        )
    }
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(username)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1, R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProfilesProperties()
        profileObserver()
        setViewPager()
        userBookmarkObeserver()
    }

    private fun userBookmarkObeserver() {
        bookmarkUserViewModel.isBookmarked(username).observe(this) { state ->
            if (state) {
                binding.fabBtn.setOnClickListener {
                    bookmarkUserViewModel.removeBookmarkedUser(username, avatarUrl)
                }
            } else {
                binding.fabBtn.setOnClickListener {
                    bookmarkUserViewModel.setBookmarkedUser(username, avatarUrl)
                }
            }
            setBookmarkedIcon(state)
        }
    }

    private fun profileObserver() {
        with(profileViewModel) {
            userProfile.observe(this@ProfileActivity) {
                showUserProfile(it)
            }
            isLoading.observe(this@ProfileActivity) {
                showLoading(it)
            }
            isNetworkFailed.observe(this@ProfileActivity) {
                showNetworkStatus(it)
            }
        }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setProfilesProperties() {
        username = intent?.getStringExtra("username").toString()
        avatarUrl = intent?.getStringExtra("avatarUrl").toString()

    }

    private fun setBookmarkedIcon(state: Boolean) {
        if (state) {
            binding.fabBtn.setImageResource(R.drawable.ic_bookmark)
        } else {
            binding.fabBtn.setImageResource(R.drawable.ic_bookmark_border)
        }
    }

    private fun showUserProfile(profile: GithubUserProfile) {
        binding.tvUsername.text = profile.name
        binding.tvDisplayName.text = profile.login
        Glide.with(this@ProfileActivity).load(profile.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivProfile)
        binding.followerStat.text = getString(R.string.follower_stat, profile.followers)
        binding.followingStat.text = getString(R.string.following_stat, profile.following)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNetworkStatus(onFailure: Boolean) {
        binding.tabs.visibility = View.GONE
        binding.viewPager.visibility = View.GONE
        binding.ivNetwork.visibility = if (onFailure) View.VISIBLE else View.GONE
    }
}