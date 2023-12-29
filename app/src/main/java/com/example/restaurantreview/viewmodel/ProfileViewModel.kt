package com.example.restaurantreview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.GithubUserProfile
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    companion object {
        var username: String? = ""
        private const val TAG = "ProfileViewModel"
    }

    private val _userProfile = MutableLiveData<GithubUserProfile>()
    val userProfile: LiveData<GithubUserProfile> = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower: LiveData<List<ItemsItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    // TODO: Kepanggil dua kali ???
    //  Iya karena 2 kali di init di UserProfileActivity + FollowStatsFragment
    init {
        showProfile(username!!)
    }

    private fun showProfile(user: String) {
        // TODO: remove log nya sebelum submit
        Log.d(TAG, "showProfile ke panggil lagi ni")
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserProfile(user)
        client.enqueue(object : Callback<GithubUserProfile> {
            override fun onResponse(
                call: Call<GithubUserProfile>,
                response: Response<GithubUserProfile>,
            ) {
                _isLoading.value = false
                if (response.body() != null) {
                    _userProfile.value = response.body()
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

    fun showFollowers(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (!response.body().isNullOrEmpty()) {
                    _listFollower.value = response.body()
                } else {
                    _listFollower.value = listOf()
                    Log.e(TAG, "_listFollower onFailure: $response")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                // TODO: remove log nya sebelum submit
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun showFollowing(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowings(user)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (!response.body().isNullOrEmpty()) {
                    _listFollowing.value = response.body()
                } else {
                    _listFollower.value = listOf()
                    Log.e(TAG, "_listFollowing onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}