package com.example.restaurantreview.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityMainBinding
import com.example.restaurantreview.ui.darkmode.DarkModeActivity
import com.example.restaurantreview.ui.darkmode.DarkModeViewModel
import com.example.restaurantreview.ui.darkmode.DarkModeViewModelFactory
import com.example.restaurantreview.ui.darkmode.SettingPreferences
import com.example.restaurantreview.ui.darkmode.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SearchViewModel::class.java]
        val pref = SettingPreferences.getInstance(application.dataStore)
        val darkModeViewModel =
            ViewModelProvider(this, DarkModeViewModelFactory(pref))[DarkModeViewModel::class.java]

        darkModeViewModel.getThemeSettings().observe(this) { isDarkActive: Boolean ->
            if (isDarkActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.d("MainActivity", "Dark Mode Active")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.d("MainActivity", "Dark Mode Deactive")
            }
            searchViewModel.darkMode(isDarkActive)
        }

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
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.bookmark_profile -> {
                        Log.d("MainActivity", "bookmark trigger")
                        true
                    }

                    R.id.dark_mode -> {
                        Log.d("MainActivity", "darkmode trigger")
                        startActivity(Intent(this@MainActivity, DarkModeActivity::class.java))
                        true
                    }

                    else -> false
                }
            }
        }

        searchViewModel.users.observe(this) {
            displayResult()
        }

        searchViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        searchViewModel.isNetworkFailed.observe(this) {
            showNetworkStatus(it)
        }

        searchViewModel.darkMode.observe(this) {
            displayResult()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)
    }

    override fun onRestart() {
        Log.d("MainActivity", "onRestart")
        super.onRestart()
    }

    private fun displayResult() {
        val adapter = SearchResultAdapter()
        adapter.submitList(searchViewModel.users.value)
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