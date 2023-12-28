package com.example.restaurantreview.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantreview.ui.FollowStatsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowStatsFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowStatsFragment.ARG_POSITION, position + 1)
            putString(FollowStatsFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}