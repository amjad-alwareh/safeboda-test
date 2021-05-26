package com.safeboda.test.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.data.repository.UserRepository
import com.safeboda.test.user.usecase.UserUseCaseImpl
import com.safeboda.test.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

private const val NAME = "Amjad Alwareh"
private const val SEARCH_USERNAME = "amjad-alwareh"

class UserUseCaseShould : BaseUnitTest() {

    private val repository = mock<UserRepository>()
    private val user = mock<User>()
    private val expectedSuccessResult = Result.success(user)
    private val expectedErrorResult = Result.failure<User>(Exception("An error occurred"))

    init {
        whenever(user.name).thenReturn(NAME)
    }

    @Test
    fun fetchUserFromUseCase() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        useCase.searchUser(SEARCH_USERNAME)
        verify(repository, times(1)).searchUser(SEARCH_USERNAME)
    }

    @Test
    fun emitUserFromUseCase() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        Assert.assertEquals(
            NAME,
            useCase.searchUser(SEARCH_USERNAME).first().getOrNull()?.name
        )
    }

    @Test
    fun emitErrorWhenReceiveErrorFromUseCase() = runBlockingTest {
        val useCase = mockErrorCase()
        Assert.assertEquals(
            expectedErrorResult.exceptionOrNull(),
            useCase.searchUser(SEARCH_USERNAME).first().exceptionOrNull()
        )
    }

    private suspend fun mockSuccessfulCase(): UserUseCaseImpl {
        whenever(repository.searchUser(SEARCH_USERNAME)).thenReturn(flow {
            emit(expectedSuccessResult)
        })
        return UserUseCaseImpl(repository)
    }

    private suspend fun mockErrorCase(): UserUseCaseImpl {
        whenever(repository.searchUser(SEARCH_USERNAME)).thenReturn(flow {
            emit(expectedErrorResult)
        })
        return UserUseCaseImpl(repository)
    }

}