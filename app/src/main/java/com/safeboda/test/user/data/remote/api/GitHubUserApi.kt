package com.safeboda.test.user.data.remote.api

import com.safeboda.test.BuildConfig
import com.safeboda.test.user.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubUserApi {

    @GET(BuildConfig.API_SEARCH_USER)
    suspend fun searchUser(@Path("username") username: String): Response<User>

}