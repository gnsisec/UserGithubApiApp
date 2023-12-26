package com.example.restaurantreview.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantreview.data.response.CustomerReviewsItem
import com.example.restaurantreview.data.response.Restaurant
import com.example.restaurantreview.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

//        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
//        mainViewModel.restaurant.observe(this) {
//            setRestaurantData(it)
//        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

//        mainViewModel.listReview.observe(this) {
//            setReviewData(it)
//        }
//
//        mainViewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
//
//        mainViewModel.snackBar.observe(this) {
//            it.getContentIfNotHandled()?.let { snackBar ->
//                Snackbar.make(
//                    window.decorView.rootView,
//                    snackBar,
//                    Snackbar.LENGTH_SHORT
//                ).show()
//            }
//        }
    }

    private fun showLoading(isLoading : Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}