package com.jihan.app.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.app.domain.networking.SocketHandler
import com.jihan.app.domain.utils.Constants.TOKEN
import com.jihan.app.domain.utils.DatastoreUtil
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TokenViewmodel(
    private val datastoreUtil: DatastoreUtil
) : ViewModel() {

    private val _isFetchingToken = MutableStateFlow(true)
    val isFetchingToken: StateFlow<Boolean> = _isFetchingToken

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token


    init {
        fetchToken()
    }



    private fun fetchToken() {
        _isFetchingToken.value = true
        viewModelScope.launch {
            val mToken = datastoreUtil.readData("token")
            _token.value = mToken
            _isFetchingToken.value = false
        }

    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            datastoreUtil.saveData(TOKEN, token)
            fetchToken()
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            datastoreUtil.clearData("token")
        }
    }


}