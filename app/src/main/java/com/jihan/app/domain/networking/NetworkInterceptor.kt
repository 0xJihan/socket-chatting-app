package com.jihan.app.domain.networking

import android.content.Context
import android.util.Log
import com.jihan.app.domain.utils.Constants.TAG
import com.jihan.app.domain.utils.Constants.TOKEN
import com.jihan.app.domain.utils.DatastoreUtil
import com.jihan.app.domain.viewmodel.TokenViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.math.log

class NetworkInterceptor(private val tokenViewmodel: TokenViewmodel) : Interceptor {
     override fun intercept(chain: Interceptor.Chain): Response {

        val token = tokenViewmodel.token.value

        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
         Log.w(TAG, "intercept: $token")
        return chain.proceed(request.build())
    }
}