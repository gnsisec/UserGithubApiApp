package com.example.restaurantreview.data.retrofit

import com.example.restaurantreview.data.response.GithubSearchUser
import com.example.restaurantreview.data.response.GithubUserProfile
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.data.response.PostReviewResponse
import com.example.restaurantreview.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUser (@Query("q") q : String ) : Call<GithubSearchUser>

    @GET("users/{username}")
    fun getUserProfile (@Path("username") username : String ) : Call<GithubUserProfile>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("detail/{id}")
    fun getRestaurant (@Path("id") id : String) : Call<RestaurantResponse>

    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview (
        @Field("id") id : String,
        @Field("name") name : String,
        @Field("review") review : String
    ) : Call<PostReviewResponse>
}