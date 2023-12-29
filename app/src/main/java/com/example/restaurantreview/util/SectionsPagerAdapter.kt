package com.example.restaurantreview.util

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantreview.ui.FollowStatsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""
    companion object {
        private const val TAG = "SectionPagerAdapter"
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowStatsFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowStatsFragment.ARG_POSITION, position + 1)
            putString(FollowStatsFragment.ARG_USERNAME, username)
        }
        // TODO: remove log nya sebelum submit
        Log.d(TAG, "create Fragment $username with ${position + 1}")
        return fragment
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount Fragment $username")
        return 2
    }
}