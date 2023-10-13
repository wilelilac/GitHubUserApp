package com.mwldnalf.githubuserapp.data.retrofit

import com.mwldnalf.githubuserapp.data.response.DetailUserResponse
import com.mwldnalf.githubuserapp.data.response.GitHubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUser(@Query("q") username: String) : Call<GitHubResponse>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String) : Call<List<DetailUserResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String) : Call<List<DetailUserResponse>>
}