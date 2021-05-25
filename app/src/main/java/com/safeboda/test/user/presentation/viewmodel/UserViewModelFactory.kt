package com.safeboda.test.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.safeboda.test.user.usecase.UserUseCase
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(
    private val useCase: UserUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = UserViewModel(useCase) as T

}