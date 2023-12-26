package com.example.restaurantreview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.data.response.Restaurant

class SearchViewModel : ViewModel() {
    companion object {

    }

    private val _name = MutableLiveData<List<ItemsItem>>()
    val name: LiveData<List<ItemsItem>> = _name
}