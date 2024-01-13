package com.example.restaurantreview.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restaurantreview.data.local.entity.BookmarkedUserEntity

@Database(entities = [BookmarkedUserEntity::class], version = 1, exportSchema = false)
abstract class BookmarkedUserDatabase : RoomDatabase() {
    abstract fun bookmarkedUserDao(): BookmarkedUserDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkedUserDatabase? = null
        fun getDatabase(context: Context): BookmarkedUserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkedUserDatabase::class.java,
                    "bookmarked_user"
                ).build()
            }
    }
}