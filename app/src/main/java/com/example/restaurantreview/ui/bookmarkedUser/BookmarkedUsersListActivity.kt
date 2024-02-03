package com.example.restaurantreview.ui.bookmarkedUser

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.remote.response.UserAttributes
import com.example.restaurantreview.databinding.ActivityBookmarkedUsersBinding
import com.example.restaurantreview.ui.main.SearchResultAdapter

class BookmarkedUsersListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkedUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkedUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        val adapter = SearchResultAdapter()
        val bookmarkedUsersListViewModelFactory: BookmarkedUsersListViewModelFactory =
            BookmarkedUsersListViewModelFactory.getInstance(application)
        val bookmarkedUsersListViewModel: BookmarkedUsersListViewModel by viewModels { bookmarkedUsersListViewModelFactory }

        binding.rvReview.addItemDecoration(itemDecoration)

        bookmarkedUsersListViewModel.getAllBookmarked()
            .observe(this@BookmarkedUsersListActivity) { users ->
                val items = arrayListOf<UserAttributes>()
                users.map {
                    val item = UserAttributes(login = it.username, avatarUrl = it.avatarUrl)
                    items.add(item)
                }
                adapter.submitList(items)
            }
        binding.rvReview.layoutManager = layoutManager
        binding.rvReview.visibility = View.VISIBLE
        binding.rvReview.adapter = adapter
    }
}