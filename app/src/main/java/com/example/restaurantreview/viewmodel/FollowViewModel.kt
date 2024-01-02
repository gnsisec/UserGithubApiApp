package com.example.restaurantreview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel(user: String) : ViewModel() {

    companion object {
        private const val TAG = "FollowViewModel"
    }

    private val username = user

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower: LiveData<List<ItemsItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getFollowers()
        getFollowing()
    }

    fun getFollowers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (!response.body().isNullOrEmpty()) {
                    Log.d(TAG, "getFollowers di panggil!")
                    _listFollower.value = response.body()
                } else {
                    _listFollower.value = listOf()
                    Log.e(TAG, "_listFollower onFailure: $response")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowings(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (!response.body().isNullOrEmpty()) {
                    _listFollowing.value = response.body()
                    Log.d(TAG, "getFollowing di panggil!")
                } else {
                    _listFollowing.value = listOf()
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