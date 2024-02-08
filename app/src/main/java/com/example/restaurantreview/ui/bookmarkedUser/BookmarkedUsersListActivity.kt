package com.example.restaurantreview.ui.bookmarkedUser

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.remote.response.UserAttributes
import com.example.restaurantreview.databinding.ActivityBookmarkedUsersBinding
import com.example.restaurantreview.ui.main.UserListAdapter
import com.example.restaurantreview.utils.BookmarkUserViewModelFactory

class BookmarkedUsersListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkedUsersBinding
    private val adapter : UserListAdapter = UserListAdapter()
    private val bookmarkUserViewModel: BookmarkUserViewModel by viewModels { BookmarkUserViewModelFactory.getInstance(application) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkedUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBookmarkedUsers()
        initBookmarkedUsersLayout()
    }

    private fun initBookmarkedUsersLayout() {
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.visibility = View.VISIBLE
        binding.rvUsers.adapter = adapter
    }

    private fun getBookmarkedUsers() {
        bookmarkUserViewModel.getAllBookmarked()
            .observe(this@BookmarkedUsersListActivity) { users ->
                val items = arrayListOf<UserAttributes>()
                users.map {
                    val item = UserAttributes(login = it.username, avatarUrl = it.avatarUrl)
                    items.add(item)
                }
                adapter.submitList(items)
            }
    }
}