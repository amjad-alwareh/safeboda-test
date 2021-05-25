package com.safeboda.test.user.data.local.source

import com.safeboda.test.user.data.model.User

interface UserLocalDataSource {

    suspend fun getUserByUsername(username: String): User?

    suspend fun insertUser(user: User): Long
}