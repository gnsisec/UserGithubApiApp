package com.example.restaurantreview.ui.bookmarkedUser

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantreview.data.BookmarkedUserRepository
import com.example.restaurantreview.di.Injection

class BookmarkedUsersListViewModelFactory private constructor(private val bookmarkedUserRepository: BookmarkedUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkedUsersListViewModel::class.java)) {
            return BookmarkedUsersListViewModel(bookmarkedUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: BookmarkedUsersListViewModelFactory? = null
        fun getInstance(context: Context): BookmarkedUsersListViewModelFactory =
            instance ?: synchronized(this) {
                instance
                    ?: BookmarkedUsersListViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}