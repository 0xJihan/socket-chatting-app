package com.jihan.app.presentation.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jihan.app.CenterBox
import com.jihan.app.domain.viewmodel.TokenViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreen(
    tokenViewmodel: TokenViewmodel = koinViewModel()
) {
    CenterBox {
        Text("Signup Screen")

        Button(onClick = {
            tokenViewmodel.saveToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RlbWFpbGEiLCJpYXQiOjE3MzYwOTk5MzV9.PgF7YbwjTsnwSb-ezeAHj_1Ug3tGKIn9c3hIQW7WVVM")
        }) {
            Text("Save token now")
        }
    }
}