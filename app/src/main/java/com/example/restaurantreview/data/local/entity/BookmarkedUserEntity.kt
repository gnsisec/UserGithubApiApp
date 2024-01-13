package com.example.restaurantreview.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookmarked_user")
data class BookmarkedUserEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "sapri",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = "lalala"
)