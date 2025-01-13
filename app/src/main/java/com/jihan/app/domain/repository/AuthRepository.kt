package com.jihan.app.domain.repository

import com.jihan.app.domain.model.LoginRequest
import com.jihan.app.domain.model.SignupRequest
import com.jihan.app.domain.model.UserResponse
import com.jihan.app.domain.networking.AuthApi
import com.jihan.app.domain.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import retrofit2.Response

class AuthRepository(
    private val authApi: AuthApi
) {

    private val userResponseState = MutableStateFlow<UiState<UserResponse>>(UiState.Empty)
    val userResponse = userResponseState.asStateFlow()

    suspend fun login(loginRequest: LoginRequest){
        userResponseState.emit(UiState.Loading)
        try {
        handleResponse(authApi.login(loginRequest), userResponseState)
        }catch (e:Exception){
            userResponseState.emit(UiState.Error(e.message.toString()))
        }
    }

    suspend fun signup(signupRequest: SignupRequest){
        userResponseState.emit(UiState.Loading)
        try {

        handleResponse(authApi.signup(signupRequest), userResponseState)
        }catch (e:Exception){
            userResponseState.emit(UiState.Error(e.message.toString()))
        }
    }



    private suspend fun <T> handleResponse(
        response: Response<T>,
        stateFlow: MutableStateFlow<UiState<T>>,
    ) {
        if (response.isSuccessful) {
            stateFlow.emit(UiState.Success(response.body()!!))
        } else {
            val errorMessage = response.errorBody()?.let {
                JSONObject(it.charStream().readText()).getString("message")
            } ?: response.message()
            stateFlow.emit(UiState.Error(errorMessage))
        }
    }



}