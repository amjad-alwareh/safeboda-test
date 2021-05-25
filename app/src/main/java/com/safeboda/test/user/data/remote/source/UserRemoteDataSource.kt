package com.safeboda.test.user.data.remote.source

import com.safeboda.test.user.data.model.User
import retrofit2.Response

interface UserRemoteDataSource {

    suspend fun searchUser(username: String): Response<User>

}