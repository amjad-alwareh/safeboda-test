package com.safeboda.test.user.data.repository

import com.safeboda.test.core.exception.NotFoundException
import com.safeboda.test.user.data.local.source.UserLocalDataSource
import com.safeboda.test.user.data.remote.source.UserRemoteDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.HttpURLConnection
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val local: UserLocalDataSource
) : UserRepository {

    /**
     * Check the user if exists on the database, if yes then return it, otherwise
     * request the user from remote and insert into the database then return it.
     */
    override suspend fun searchUser(username: String) = flow {
        val localRow = local.getUserByUsername(username)

        if (localRow != null) {
            emit(Result.success(localRow))
        } else {
            val remoteResponse = remote.searchUser(username)

            if (remoteResponse.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                emit(Result.failure(NotFoundException()))
                return@flow
            }

            if (!remoteResponse.isSuccessful) {
                emit(Result.failure(Exception("An Error Occurred")))
                return@flow
            }

            val user = remoteResponse.body()
            val insertResult = local.insertUser(user!!)
            emit(Result.success(user))

        }
    }.catch {
        emit(Result.failure(it))
    }

}