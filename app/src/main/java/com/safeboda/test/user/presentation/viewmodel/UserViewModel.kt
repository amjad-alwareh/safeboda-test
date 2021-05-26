package com.safeboda.test.user.presentation.viewmodel

import androidx.lifecycle.*
import com.safeboda.test.core.DataResult
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.usecase.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    private val _userResponse = MutableLiveData<DataResult<User?>>()
    val userResponse: LiveData<DataResult<User?>> = _userResponse

    fun searchUser(username: String) {
        /**[Dispatchers.IO] used for database insert operation**/
        _userResponse.postValue(DataResult.loading())

        viewModelScope.launch(Dispatchers.IO) {
            useCase.searchUser(username).collect {
                if (it.isSuccess) _userResponse.postValue(DataResult.success(it.getOrNull()))
                else _userResponse.postValue(DataResult.error(it.exceptionOrNull()))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}