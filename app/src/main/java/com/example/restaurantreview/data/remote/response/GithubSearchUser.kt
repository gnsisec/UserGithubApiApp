package com.example.restaurantreview.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubSearchUser(

    @field:SerializedName("items")
    val items: List<UserAttributes>? = null,
)

data class UserAttributes(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
)
