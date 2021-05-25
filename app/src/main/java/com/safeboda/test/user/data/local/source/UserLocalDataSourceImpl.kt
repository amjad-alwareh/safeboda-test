package com.safeboda.test.user.data.local.source

import com.safeboda.test.core.SafeBoadDatabase
import com.safeboda.test.user.data.model.User
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val database: SafeBoadDatabase
) : UserLocalDataSource {

    override suspend fun getUserByUsername(username: String) = database.userDao().getUserByUsername(username)

    override suspend fun insertUser(user: User) = database.userDao().insertUser(user)

}