package com.safeboda.test.user.data.remote.source

import com.safeboda.test.user.data.remote.api.GitHubUserApi
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val api: GitHubUserApi
) : UserRemoteDataSource {

    override suspend fun searchUser(username: String) = api.searchUser(username)

}