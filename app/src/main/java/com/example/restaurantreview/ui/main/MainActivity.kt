package com.example.restaurantreview.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.response.UserAttributes
import com.example.restaurantreview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView.hide()
                    searchBar.setText(searchView.text.toString())
                    searchViewModel.searchUser(searchBar.text.toString())
                    false
                }
        }

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

        searchViewModel.isNetworkFailed.observe(this) {
            showNetworkStatus(it)
        }
    }

    private fun displayResult(searchResult: List<UserAttributes>) {
        val adapter = SearchResultAdapter()
        adapter.submitList(searchResult)
        binding.rvReview.visibility = View.VISIBLE
        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNetworkStatus(onFailure: Boolean) {
        binding.rvReview.visibility = View.GONE
        binding.ivNetwork.visibility = if (onFailure) View.VISIBLE else View.GONE
    }
}