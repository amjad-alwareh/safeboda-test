package com.safeboda.test.user

import com.safeboda.test.user.presentation.viewmodel.UserViewModel
import com.safeboda.test.user.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class FakeUserModule {

    @Provides
    fun provideUseCase(): UserUseCase = FakeUserUseCase

    @Provides
    fun provideViewModel(useCase: UserUseCase): UserViewModel = UserViewModel(useCase)

}