package com.safeboda.test.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.presentation.viewmodel.UserViewModel
import com.safeboda.test.user.usecase.UserUseCase
import com.safeboda.test.utils.BaseUnitTest
import com.safeboda.test.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

private const val SEARCH_USERNAME = "amjad-alwareh"

class UserViewModelShould : BaseUnitTest() {

    private var useCase = mock<UserUseCase>()
    private val user = mock<User>()
    private val expectedResult = Result.success(user)
    private val expectedException = Result.failure<User>(Exception("An error occurred"))

    @Test
    fun fetchUserFromViewModel() = runBlockingTest {
        mockSuccessfulCase().searchUser(SEARCH_USERNAME)
        verify(useCase, times(1)).searchUser(SEARCH_USERNAME)
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest{
        val viewModel = mockErrorCase()
        viewModel.searchUser(SEARCH_USERNAME)
        Assert.assertEquals(expectedException.exceptionOrNull(), viewModel.userResponse.getValueForTest()?.throwable)
    }

    @Test
    fun emitsUserFromViewModel() = runBlockingTest{
        val viewModel = mockSuccessfulCase()
        viewModel.searchUser(SEARCH_USERNAME)
        Assert.assertEquals(expectedResult.getOrNull(), viewModel.userResponse.getValueForTest()?.data)
    }

    private fun mockSuccessfulCase(): UserViewModel {
        runBlocking {
            whenever(useCase.searchUser(SEARCH_USERNAME))
                .thenReturn(flow {
                    emit(expectedResult)
                })
        }
        return UserViewModel(useCase)
    }

    private suspend fun mockErrorCase(): UserViewModel {
        whenever(useCase.searchUser(SEARCH_USERNAME))
            .thenReturn(flow {
                emit(expectedException)
            })
        return UserViewModel(useCase)
    }

}