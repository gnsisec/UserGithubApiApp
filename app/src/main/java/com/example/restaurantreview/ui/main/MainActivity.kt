package com.example.restaurantreview.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityMainBinding
import com.example.restaurantreview.ui.bookmarkedUser.BookmarkedUsersListActivity
import com.example.restaurantreview.ui.darkmode.DarkModeActivity
import com.example.restaurantreview.ui.darkmode.DarkModeViewModel
import com.example.restaurantreview.ui.darkmode.DarkModeViewModelFactory
import com.example.restaurantreview.ui.darkmode.SettingPreferences
import com.example.restaurantreview.ui.darkmode.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var darkModeConfig: SettingPreferences
    private val searchViewModel : SearchViewModel by viewModels()
    private val darkModeViewModel : DarkModeViewModel by viewModels { DarkModeViewModelFactory(darkModeConfig) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RestaurantReview)
        darkModeConfig = SettingPreferences.getInstance(application.dataStore)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getThemeSetting()
        setSearchBarMenu()
        initSearchLayout()
        searchObserver()
    }

    private fun searchObserver() {
        with(searchViewModel) {
            searchResult.observe(this@MainActivity) {
                displayResult()
            }

            isLoading.observe(this@MainActivity) {
                showLoading(it)
            }

            isNetworkFailed.observe(this@MainActivity) {
                updateNetworkState(it)
            }

            isDarkMode.observe(this@MainActivity) {
                displayResult()
            }
        }
    }

    private fun initSearchLayout() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }

    private fun setSearchBarMenu() {
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
                        startActivity(
                            Intent(
                                this@MainActivity,
                                BookmarkedUsersListActivity::class.java
                            )
                        )
                        true
                    }

                    R.id.dark_mode -> {
                        startActivity(Intent(this@MainActivity, DarkModeActivity::class.java))
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun getThemeSetting() {
        darkModeViewModel.getThemeSettings().observe(this) { isActive: Boolean ->
            if (isActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            searchViewModel.darkMode(isActive)
        }
    }

    private fun displayResult() {
        val adapter = UserListAdapter()
        adapter.submitList(searchViewModel.searchResult.value)
        binding.rvUsers.visibility = View.VISIBLE
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun updateNetworkState(onFailure: Boolean) {
        binding.rvUsers.visibility = View.GONE
        binding.ivNetwork.visibility = if (onFailure) View.VISIBLE else View.GONE
    }
}