package com.example.restaurantreview.di

import android.content.Context
import com.example.restaurantreview.data.BookmarkedUserRepository
import com.example.restaurantreview.data.local.room.BookmarkedUserDatabase

object Injection {
    fun provideRepository(context: Context): BookmarkedUserRepository {
        val database = BookmarkedUserDatabase.getDatabase(context)
        val dao = database.bookmarkedUserDao()
        return BookmarkedUserRepository.getInstance(dao)
    }
}