package com.example.restaurantreview.data.retrofit

import com.example.restaurantreview.data.response.GithubSearchUser
import com.example.restaurantreview.data.response.GithubUserProfile
import com.example.restaurantreview.data.response.UserAttributes
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUser(@Query("q") q: String): Call<GithubSearchUser>

    @GET("users/{username}")
    fun getUserProfile(@Path("username") username: String): Call<GithubUserProfile>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserAttributes>>

    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username: String): Call<List<UserAttributes>>

}