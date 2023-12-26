package com.example.restaurantreview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.GithubSearchUser
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _itemList = MutableLiveData<List<ItemsItem>>()
    val itemList: LiveData<List<ItemsItem>> = _itemList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchUser("matt")
//        searchUser("oqoweksdf")
    }

    private fun searchUser( user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearchUser(user)
        client.enqueue( object : Callback<GithubSearchUser> {
            override fun onResponse(
                call: Call<GithubSearchUser>,
                response: Response<GithubSearchUser>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _itemList.value = response.body()?.items!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubSearchUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}