package com.jihan.app.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.app.domain.model.LoginRequest
import com.jihan.app.domain.model.SignupRequest
import com.jihan.app.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewmodel(
    private val repository: AuthRepository
) : ViewModel() {

    val userResponse = repository.userResponse

    fun login(loginRequest: LoginRequest) {

        viewModelScope.launch {
        repository.login(loginRequest)
        }
    }

    fun signup(signupRequest: SignupRequest) {
        viewModelScope.launch {
        repository.signup(signupRequest)
        }
    }


}