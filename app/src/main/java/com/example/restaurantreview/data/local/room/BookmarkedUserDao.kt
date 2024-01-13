package com.example.restaurantreview.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantreview.data.local.entity.BookmarkedUserEntity

@Dao
interface BookmarkedUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user : BookmarkedUserEntity )

    @Delete
    suspend fun delete(user: BookmarkedUserEntity)

    @Query("SELECT * FROM bookmarked_user ORDER BY username ASC")
    fun getAllBookmarkedUser(): LiveData<List<BookmarkedUserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM bookmarked_user WHERE username = :user)")
    fun isUserBookmarked(user: String) : LiveData<Boolean>
}