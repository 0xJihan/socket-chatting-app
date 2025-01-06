package com.jihan.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jihan.app.domain.Route
import com.jihan.app.domain.networking.SocketHandler
import com.jihan.app.domain.utils.Constants.TAG
import com.jihan.app.domain.utils.DatastoreUtil
import com.jihan.app.domain.viewmodel.TokenViewmodel
import com.jihan.app.presentation.screens.HomeScreen
import com.jihan.app.presentation.screens.LoginScreen
import com.jihan.app.presentation.screens.SignupScreen
import com.jihan.app.ui.theme.AppTheme
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {




    private val tokenViewmodel : TokenViewmodel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

            SocketHandler.setSocket("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RlbWFpbGEiLCJpYXQiOjE3MzYwOTk5MzV9.PgF7YbwjTsnwSb-ezeAHj_1Ug3tGKIn9c3hIQW7WVVM")
            SocketHandler.establishConnection()

        splashScreen.setKeepOnScreenCondition {
            tokenViewmodel.isFetchingToken.value
        }



        setContent {
            AppTheme {
               MainApp()
            }
        }
    }

    @Composable
    fun MainApp() {
        val token by tokenViewmodel.token.collectAsState()
        val navController = rememberNavController()
        val startDestination = if (token==null) Route.Signup else Route.Home


        NavHost(navController,startDestination){

            composable<Route.Login> {
                LoginScreen()
            }

            composable<Route.Home> { HomeScreen() }

            composable<Route.Signup> {
                SignupScreen()
            }
        }

    }

}



@Composable
fun CenterBox(modifier: Modifier = Modifier.fillMaxSize(),content : @Composable ColumnScope.() -> Unit) {
   Column(modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {content()}
}

