package com.jihan.app.domain.utils

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

object HelperClass {

    fun validateUserCredentials(
        userName: String = "Default",
        email: String,
        password: String,
        confirmPassword:String = password
    ): Pair<Boolean, String> {

        val result = Pair(true, "")

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return Pair(false, "Please Provide All Required Information")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Pair(false, "Invalid Email Address")
        } else if (password.length < 5) {
            return Pair(false, "Password should be at least 5 characters long")
        }
        else if (password != confirmPassword){
            return Pair(false, "Passwords do not match")
        }


        return result
    }


}

fun String.toFormData(key:String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(key,this)
}

fun Uri?.toMultipart(context: Context): MultipartBody.Part? {


    if (this == null) return null
    else {

        val filesDir = context.filesDir //? private files directory of the app

        val file = File(filesDir, "image.jpg")

        context.contentResolver.openInputStream(this)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image", file.name, requestBody)

        return part
    }

}


fun String.toast(context: Context): Unit {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}