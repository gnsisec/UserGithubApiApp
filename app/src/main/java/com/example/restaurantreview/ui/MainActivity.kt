package com.example.restaurantreview.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.databinding.ActivityMainBinding
import com.example.restaurantreview.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel by viewModels<SearchViewModel>()

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

        searchViewModel.itemList.observe(this) {
            displayResult(it)
        }

        searchViewModel.isLoading.observe(this) {
            showLoading(it)
        }
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

    private fun displayResult(searchResult: List<ItemsItem>) {
        val adapter = SearchResultAdapter()
        adapter.submitList(searchResult)
        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}