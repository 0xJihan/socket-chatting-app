package com.jihan.app.presentation.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jihan.app.domain.viewmodel.TokenViewmodel
import com.jihan.app.presentation.screens.components.CenterBox
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(tokenViewmodel: TokenViewmodel = koinViewModel()) {
    val token by tokenViewmodel.token.collectAsStateWithLifecycle()
    CenterBox {
        Text("Home Screen",color=MaterialTheme.colorScheme.onSurface)
        Text(token.toString(),color=MaterialTheme.colorScheme.onSurface)
    }
}