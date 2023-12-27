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

class ProfileViewModel : ViewModel() {
    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val _avatar = MutableLiveData<String>()
    val avatar: LiveData<String> = _avatar

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _displayName = MutableLiveData<String>()
    val displayName: LiveData<String> = _displayName

    private val _followings = MutableLiveData<Int>()
    val followings: LiveData<Int> = _followings

    private val _followers = MutableLiveData<Int>()
    val followers: LiveData<Int> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun showProfile(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserProfile(user)
        client.enqueue(object : Callback<GithubUserProfile> {
            override fun onResponse(
                call: Call<GithubUserProfile>,
                response: Response<GithubUserProfile>,
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.body() != null) {
                    _username.value = responseBody?.login
                    _displayName.value = responseBody?.name
                    _followings.value = responseBody?.following
                    _followers.value = responseBody?.followers
                    _avatar.value = responseBody?.avatarUrl
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