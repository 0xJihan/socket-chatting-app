package com.jihan.app.domain.model

data class UserResponse(
    val message:String,
    val success : Boolean,
    val token : String
)