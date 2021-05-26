package com.safeboda.test.user.data.remote.source

import com.safeboda.test.core.exception.NotFoundException
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.data.remote.api.GitHubUserApi
import java.net.HttpURLConnection
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val api: GitHubUserApi
) : UserRemoteDataSource {

    override suspend fun searchUser(username: String): User? {
        val response = api.searchUser(username)

        return when {
            response.code() == HttpURLConnection.HTTP_NOT_FOUND -> throw NotFoundException()
            !response.isSuccessful -> throw Exception("An Error Occurred")
            else -> response.body()
        }

    }

}