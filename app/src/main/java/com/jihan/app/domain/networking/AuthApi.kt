package com.jihan.app.domain.networking

import com.jihan.app.domain.model.LoginRequest
import com.jihan.app.domain.model.SignupRequest
import com.jihan.app.domain.model.UploadResponse
import com.jihan.app.domain.model.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {


    @POST("/login")
    suspend fun login(
         @Body loginRequest: LoginRequest
    ) : Response<UserResponse>


    @POST("/signup")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ) : Response<UserResponse>

    @Multipart
    @POST("/upload")
    suspend fun upload(
        @Part image:MultipartBody.Part
    ) : Response<UploadResponse>

}