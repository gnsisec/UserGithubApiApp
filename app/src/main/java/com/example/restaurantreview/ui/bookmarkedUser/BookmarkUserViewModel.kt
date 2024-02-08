package com.example.restaurantreview.ui.bookmarkedUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreview.data.BookmarkedUserRepository
import com.example.restaurantreview.data.local.entity.BookmarkedUserEntity
import kotlinx.coroutines.launch

class BookmarkUserViewModel(private val bookmarkedUserRepository: BookmarkedUserRepository) :
    ViewModel() {
    private val bookmarkedUserEntity = BookmarkedUserEntity()

    fun getAllBookmarked(): LiveData<List<BookmarkedUserEntity>> {
        return bookmarkedUserRepository.getAllBookmarked()
    }

    fun isBookmarked(username: String): LiveData<Boolean> {
        return bookmarkedUserRepository.isUserBookmarked(username)
    }

    fun setBookmarkedUser(username: String, avatarUrl: String) {
        viewModelScope.launch {
            bookmarkedUserEntity.username = username
            bookmarkedUserEntity.avatarUrl = avatarUrl
            bookmarkedUserRepository.bookmarkUser(bookmarkedUserEntity)
            bookmarkedUserRepository.isUserBookmarked(username)
        }
    }

    fun removeBookmarkedUser(username: String, avatarUrl: String) {
        viewModelScope.launch {
            bookmarkedUserEntity.username = username
            bookmarkedUserEntity.avatarUrl = avatarUrl
            bookmarkedUserRepository.removeBookmarkUser(bookmarkedUserEntity)
            bookmarkedUserRepository.isUserBookmarked(username)
        }
    }
}