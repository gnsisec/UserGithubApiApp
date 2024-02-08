package com.example.restaurantreview.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantreview.data.BookmarkedUserRepository
import com.example.restaurantreview.di.Injection
import com.example.restaurantreview.ui.bookmarkedUser.BookmarkUserViewModel

class BookmarkUserViewModelFactory private constructor(private val bookmarkedUserRepository: BookmarkedUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkUserViewModel::class.java)) {
            return BookmarkUserViewModel(bookmarkedUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: BookmarkUserViewModelFactory? = null
        fun getInstance(context: Context): BookmarkUserViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BookmarkUserViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}