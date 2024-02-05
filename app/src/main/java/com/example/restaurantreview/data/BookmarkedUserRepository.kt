package com.example.restaurantreview.data

import androidx.lifecycle.LiveData
import com.example.restaurantreview.data.local.entity.BookmarkedUserEntity
import com.example.restaurantreview.data.local.room.BookmarkedUserDao

class BookmarkedUserRepository private constructor(
    private val bookmarkedUserDao: BookmarkedUserDao,
) {
    suspend fun bookmarkUser(user: BookmarkedUserEntity) {
        bookmarkedUserDao.insert(user)
    }

    suspend fun removeBookmarkUser(user: BookmarkedUserEntity) {
        bookmarkedUserDao.delete(user)
    }

    fun isUserBookmarked(user: String): LiveData<Boolean> = bookmarkedUserDao.isUserBookmarked(user)

    fun getAllBookmarked(): LiveData<List<BookmarkedUserEntity>> =
        bookmarkedUserDao.getAllBookmarkedUser()

    companion object {
        @Volatile
        private var instance: BookmarkedUserRepository? = null
        fun getInstance(
            bookmarkedUserDao: BookmarkedUserDao,
        ): BookmarkedUserRepository =
            instance ?: synchronized(this) {
                instance ?: BookmarkedUserRepository(bookmarkedUserDao)
            }.also { instance = it }
    }
}