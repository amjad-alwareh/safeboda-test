package com.safeboda.test.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.safeboda.test.user.data.local.source.UserLocalDataSource
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.data.remote.source.UserRemoteDataSource
import com.safeboda.test.user.data.repository.UserRepositoryImpl
import com.safeboda.test.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

private const val NAME = "Amjad Alwareh"
private const val SEARCH_USERNAME = "amjad-alwareh"

class UserRepositoryShould : BaseUnitTest() {

    private val remoteDataSource = mock<UserRemoteDataSource>()
    private val localDataSource = mock<UserLocalDataSource>()
    private val user = mock<User>()

    init {
        whenever(user.name).thenReturn(NAME)
    }

    @Test
    fun fetchUserFromRepository() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.searchUser(SEARCH_USERNAME)
        verify(remoteDataSource, times(1)).searchUser(SEARCH_USERNAME)
    }

    @Test
    fun emitUserFromRepository() = runBlockingTest {
        val repository = mockSuccessfulCase()
        Assert.assertEquals(
            NAME,
            repository.searchUser(SEARCH_USERNAME).first().getOrNull()?.name
        )
    }

    @Test
    fun emitErrorWhenReceiveErrorFromRepository() = runBlockingTest {
        val repository = mockErrorCase()
        Assert.assertNotNull(repository.searchUser(SEARCH_USERNAME).first().exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase(): UserRepositoryImpl {
        whenever(remoteDataSource.searchUser(SEARCH_USERNAME)).thenReturn(user)
        return UserRepositoryImpl(remoteDataSource, localDataSource)
    }

    private suspend fun mockErrorCase(): UserRepositoryImpl {
        whenever(remoteDataSource.searchUser(SEARCH_USERNAME)).thenReturn(null)
        return UserRepositoryImpl(remoteDataSource, localDataSource)
    }

}