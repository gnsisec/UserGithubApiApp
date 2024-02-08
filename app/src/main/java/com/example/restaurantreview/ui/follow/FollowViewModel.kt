package com.example.restaurantreview.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.remote.response.UserAttributes
import com.example.restaurantreview.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel(private val username: String) : ViewModel() {

    companion object {
        private const val TAG = "FollowViewModel"
    }

    private val _listFollower = MutableLiveData<List<UserAttributes>>()
    val listFollower: LiveData<List<UserAttributes>> = _listFollower

    private val _listFollowing = MutableLiveData<List<UserAttributes>>()
    val listFollowing: LiveData<List<UserAttributes>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getFollowers()
        getFollowing()
    }

    private fun getFollowers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserAttributes>> {
            override fun onResponse(
                call: Call<List<UserAttributes>>,
                response: Response<List<UserAttributes>>,
            ) {
                _isLoading.value = false
                if (!response.body().isNullOrEmpty()) {
                    _listFollower.value = response.body()
                } else {
                    _listFollower.value = listOf()
                    Log.e(TAG, "_listFollower onFailure: $response")
                }
            }

            override fun onFailure(call: Call<List<UserAttributes>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getFollowing() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowings(username)
        client.enqueue(object : Callback<List<UserAttributes>> {
            override fun onResponse(
                call: Call<List<UserAttributes>>,
                response: Response<List<UserAttributes>>,
            ) {
                _isLoading.value = false
                if (!response.body().isNullOrEmpty()) {
                    _listFollowing.value = response.body()
                } else {
                    _listFollowing.value = listOf()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserAttributes>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}