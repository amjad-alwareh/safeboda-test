package com.safeboda.test.user.usecase

import com.safeboda.test.user.data.repository.UserRepository
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : UserUseCase {

    override suspend fun searchUser(username: String) = repository.searchUser(username)
}