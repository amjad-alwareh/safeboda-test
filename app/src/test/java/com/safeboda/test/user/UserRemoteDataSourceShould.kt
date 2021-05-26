package com.safeboda.test.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.data.remote.api.GitHubUserApi
import com.safeboda.test.user.data.remote.source.UserRemoteDataSource
import com.safeboda.test.user.data.remote.source.UserRemoteDataSourceImpl
import com.safeboda.test.utils.BaseUnitTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

private const val NAME = "Amjad Alwareh"
private const val SEARCH_USERNAME = "amjad-alwareh"

class UserRemoteDataSourceShould : BaseUnitTest() {

    private lateinit var remoteDataSource: UserRemoteDataSource
    private val api = mock<GitHubUserApi>()
    private val user = mock<User>()
    private val expectedErrorResult = null

    init {
        whenever(user.name).thenReturn(NAME)
    }

    @Test
    fun fetchUserFromApi() = runBlockingTest{
        remoteDataSource = mockRemoteDataSource()
        remoteDataSource.searchUser(SEARCH_USERNAME)
        verify(api, times(1)).searchUser(SEARCH_USERNAME)
    }

    @Test
    fun emitsResultFromApi() = runBlockingTest {
        mockSuccessfulCase()
        Assert.assertEquals(
            user.name,
            remoteDataSource.searchUser(SEARCH_USERNAME)?.name
        )
    }

    @Test
    fun emitErrorWhenReceiveErrorFromApi() = runBlockingTest {
        mockErrorCase()
        Assert.assertNull(remoteDataSource.searchUser(SEARCH_USERNAME))
    }

    private fun mockRemoteDataSource() = UserRemoteDataSourceImpl(api)

    private suspend fun mockSuccessfulCase() {
        whenever(api.searchUser(SEARCH_USERNAME)).thenReturn(Response.success(user))
        remoteDataSource = mockRemoteDataSource()
    }

    private suspend fun mockErrorCase() {
        whenever(api.searchUser(SEARCH_USERNAME)).thenReturn(expectedErrorResult)
        remoteDataSource = mockRemoteDataSource()
    }

}