package com.example.restaurantreview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.GithubUserProfile
import com.example.restaurantreview.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(user: String) : ViewModel() {
    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val username = user

    private val _userProfile = MutableLiveData<GithubUserProfile>()
    val userProfile: LiveData<GithubUserProfile> = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getProfile(username)
    }

    private fun getProfile(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserProfile(username)
        client.enqueue(object : Callback<GithubUserProfile> {
            override fun onResponse(
                call: Call<GithubUserProfile>,
                response: Response<GithubUserProfile>,
            ) {
                _isLoading.value = false
                if (response.body() != null) {
                    _userProfile.value = response.body()
                    Log.d(TAG, "getProfile di panggil!")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubUserProfile>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}