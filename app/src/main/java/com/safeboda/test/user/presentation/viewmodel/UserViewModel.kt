package com.safeboda.test.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safeboda.test.core.DataResult
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.usecase.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    private val _userResponse = MutableStateFlow<DataResult<User?>>(DataResult.none())
    val userResponse: StateFlow<DataResult<User?>> = _userResponse

    fun searchUser(username: String) {
        /**[Dispatchers.IO] used for database insert operation**/
        _userResponse.value = DataResult.loading()

        viewModelScope.launch(Dispatchers.IO) {
            useCase.searchUser(username).collect {
                if (it.isSuccess) _userResponse.value = DataResult.success(it.getOrNull())
                else _userResponse.value = DataResult.error(it.exceptionOrNull())
            }
        }
    }
}