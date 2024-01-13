package com.example.restaurantreview.ui.bookmarkedUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.BookmarkedUserRepository
import com.example.restaurantreview.data.local.entity.BookmarkedUserEntity

class BookmarkedUsersListViewModel(private val bookmarkedUserRepository: BookmarkedUserRepository) :
    ViewModel() {

    fun getAllBookmarked(): LiveData<List<BookmarkedUserEntity>> {
        return bookmarkedUserRepository.getAllBookmarked()
    }
}
