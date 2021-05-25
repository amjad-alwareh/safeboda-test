package com.safeboda.test.user.usecase

import com.safeboda.test.user.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    suspend fun searchUser(username: String): Flow<Result<User>>
}