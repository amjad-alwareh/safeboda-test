package com.safeboda.test.user.di

import com.safeboda.test.core.SafeBoadDatabase
import com.safeboda.test.user.data.local.source.UserLocalDataSource
import com.safeboda.test.user.data.local.source.UserLocalDataSourceImpl
import com.safeboda.test.user.data.remote.api.GitHubUserApi
import com.safeboda.test.user.data.remote.source.UserRemoteDataSource
import com.safeboda.test.user.data.remote.source.UserRemoteDataSourceImpl
import com.safeboda.test.user.data.repository.UserRepository
import com.safeboda.test.user.data.repository.UserRepositoryImpl
import com.safeboda.test.user.usecase.UserUseCase
import com.safeboda.test.user.usecase.UserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@InstallIn(ActivityComponent::class)
@Module
class UserModule {

    @Provides
    fun provideApi(retrofit: Retrofit): GitHubUserApi = retrofit.create(GitHubUserApi::class.java)

    @Provides
    fun provideRemoteDataSource(api: GitHubUserApi): UserRemoteDataSource = UserRemoteDataSourceImpl(api)

    @Provides
    fun provideLocalDataSource(database: SafeBoadDatabase): UserLocalDataSource = UserLocalDataSourceImpl(database)

    @Provides
    fun provideRepository(
        localDataSource: UserLocalDataSource,
        remoteDataSource: UserRemoteDataSource
    ): UserRepository = UserRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    fun provideUseCase(repository: UserRepository): UserUseCase = UserUseCaseImpl(repository)

}