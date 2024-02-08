package com.example.restaurantreview.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_user")
data class BookmarkedUserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "username",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = "avatarUrl",
)