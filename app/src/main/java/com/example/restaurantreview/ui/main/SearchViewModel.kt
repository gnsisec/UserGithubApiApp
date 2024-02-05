package com.example.restaurantreview.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.remote.response.GithubSearchUser
import com.example.restaurantreview.data.remote.response.UserAttributes
import com.example.restaurantreview.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel : ViewModel() {
    companion object {
        private const val TAG = "SearchViewModel"
    }


    private val _users = MutableLiveData<List<UserAttributes>>()
    val users: LiveData<List<UserAttributes>> = _users

    private val _darkMode = MutableLiveData<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNetworkFailed = MutableLiveData<Boolean>()
    val isNetworkFailed: LiveData<Boolean> = _isNetworkFailed

    init {
        searchUser("budi")
    }

    fun darkMode( mode : Boolean ) {
        _darkMode.value = mode
    }

    fun searchUser(user: String) {
        _isNetworkFailed.value = false
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearchUser(user)
        client.enqueue(object : Callback<GithubSearchUser> {
            override fun onResponse(
                call: Call<GithubSearchUser>,
                response: Response<GithubSearchUser>,
            ) {
                _isLoading.value = false
                if (response.body()?.items?.isNotEmpty() == true) {
                    _users.value = response.body()?.items!!
                    Log.e(TAG, "onSuccess: ${response.message()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubSearchUser>, t: Throwable) {
                _isLoading.value = false
                _isNetworkFailed.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}