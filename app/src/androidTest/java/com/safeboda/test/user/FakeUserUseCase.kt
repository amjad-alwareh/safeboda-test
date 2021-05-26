package com.safeboda.test.user

import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.usecase.UserUseCase
import kotlinx.coroutines.flow.flow

val user = User(1, "amjad-alwareh", "", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "", "")

object FakeUserUseCase : UserUseCase {

    var useCaseState: UseCaseState = UseCaseState.SUCCESS

    override suspend fun searchUser(username: String) = flow {
        when (useCaseState) {
            UseCaseState.SUCCESS -> emit(Result.success(user))
            UseCaseState.ERROR -> emit(Result.failure<User>(Exception("An error occurred")))
        }
    }

    enum class UseCaseState { SUCCESS, ERROR }

}