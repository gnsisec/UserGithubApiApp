package com.example.restaurantreview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FollowViewModelFactory(private val user: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java))
            return FollowViewModel(user) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }
}