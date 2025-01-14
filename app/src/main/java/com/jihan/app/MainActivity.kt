package com.jihan.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jihan.app.domain.Route
import com.jihan.app.domain.networking.SocketHandler
import com.jihan.app.domain.utils.Constants.TAG
import com.jihan.app.domain.utils.toast
import com.jihan.app.domain.viewmodel.TokenViewmodel
import com.jihan.app.presentation.screens.HomeScreen
import com.jihan.app.presentation.screens.LoginScreen
import com.jihan.app.presentation.screens.SignupScreen
import com.jihan.app.presentation.screens.components.CenterBox
import com.jihan.app.presentation.screens.components.OrbitLoading
import com.jihan.app.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {


    private val tokenViewmodel: TokenViewmodel by inject()
    private val validatedUser = MutableStateFlow(false)
    private val token = tokenViewmodel.token.asLiveData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            tokenViewmodel.isFetchingToken.value
        }



        SocketHandler.setSocket()
        if (SocketHandler.getSocket()?.connected()?.not() == true) {
            SocketHandler.establishConnection()
        }

        // Set up authentication listener first
        SocketHandler.getSocket()?.on("authenticate") { args ->
            try {
                val isValidated = args[0] as Boolean
                validatedUser.value = isValidated

                if (isValidated.not()) {
                    tokenViewmodel.clearToken()
                }

            } catch (e: Exception) {
                validatedUser.value = false
            }
        }

        SocketHandler.getSocket()?.on("error") {
            try {
                val message = it[0] as String
                runOnUiThread { message.toast(this) }
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ${e.message}")
            }
        }

        // Observe token changes and authenticate
        token.observe(this) { newToken ->
            if (!tokenViewmodel.isFetchingToken.value) {
                newToken?.let { token ->
                    SocketHandler.getSocket()?.emit("authenticate", token)
                } ?: run {
                    validatedUser.value = false
                }
            }
        }

        setContent {
            AppTheme {
                MainApp()
            }
        }


    }



    @Composable
    fun MainApp() {
        val token by tokenViewmodel.token.collectAsStateWithLifecycle()
        val isLoading by tokenViewmodel.isFetchingToken.collectAsStateWithLifecycle()
        val isValidated by validatedUser.collectAsStateWithLifecycle()
        val navController = rememberNavController()

        val startDestination = when {
            isLoading -> Route.Loading
            token == null || !isValidated -> Route.Login
            else -> Route.Home
        }

        NavHost(navController, startDestination) {
            composable<Route.Login> {
                LoginScreen(navController, tokenViewmodel = tokenViewmodel)
            }
            composable<Route.Home> {
                HomeScreen()
            }
            composable<Route.Signup> {
                SignupScreen(navController, tokenViewmodel = tokenViewmodel)
            }
            composable<Route.Loading> {
                CenterBox {
                    OrbitLoading(Modifier.size(150.dp))
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        SocketHandler.closeConnection()
        SocketHandler.getSocket()?.off("authenticate")
        SocketHandler.getSocket()?.off("error")

    }


}




