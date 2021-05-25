package com.safeboda.test.user.data.repository

import com.safeboda.test.user.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun searchUser(username: String): Flow<Result<User>>

}