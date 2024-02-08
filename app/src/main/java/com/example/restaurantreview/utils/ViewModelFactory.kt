package com.example.restaurantreview.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantreview.ui.follow.FollowViewModel
import com.example.restaurantreview.ui.profile.ProfileViewModel

class ViewModelFactory(private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java))
            return FollowViewModel(username) as T
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(username) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }
}