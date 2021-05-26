package com.safeboda.test.user.data.remote.source

import com.safeboda.test.user.data.model.User

interface UserRemoteDataSource {

    suspend fun searchUser(username: String): User?

}