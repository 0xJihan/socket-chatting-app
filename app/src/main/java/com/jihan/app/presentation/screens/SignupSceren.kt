package com.jihan.app.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jihan.app.domain.Route
import com.jihan.app.domain.model.SignupRequest
import com.jihan.app.domain.utils.HelperClass
import com.jihan.app.domain.utils.UiState
import com.jihan.app.domain.utils.toast
import com.jihan.app.domain.viewmodel.AuthViewmodel
import com.jihan.app.domain.viewmodel.TokenViewmodel
import com.jihan.app.presentation.screens.components.EditText
import com.jihan.app.presentation.screens.components.MyButton
import io.eyram.iconsax.IconSax
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreen(
    navController: NavController, authViewmodel: AuthViewmodel = koinViewModel(),
    tokenViewmodel: TokenViewmodel = koinViewModel(),
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }



    val userResponse by authViewmodel.userResponse.collectAsStateWithLifecycle()

    when (val state = userResponse){
        is UiState.Loading -> {
            loading = true
        }
        is UiState.Success -> {
            loading = false
            tokenViewmodel.saveToken(state.data.token)
            state.data.message.toast(context)
            navController.navigate(Route.Home){
                popUpTo<Route.Login> {
                    inclusive = true
                }
            }
        }
        is UiState.Error -> {
            loading = false
            state.error.toast(context)
        }
        else -> {}
    }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

            Text(
                text = "Signup",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = "Create an account to get started",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(Modifier.height(20.dp))


            EditText(
                value = name, label = "Name", leadingIcon = IconSax.Linear.User
            ) { name = it }

            EditText(
                value = email, label = "Email", leadingIcon = IconSax.Linear.Sms
            ) { email = it }
            EditText(
                value = password,
                label = "Password",
                leadingIcon = IconSax.Linear.Lock,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                isPasswordFieldType = true
            ) { password = it }

            EditText(
                value = confirmPassword,
                label = "Confirm Password",
                leadingIcon = IconSax.Linear.Lock,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                isPasswordFieldType = true
            ) { confirmPassword = it }


            TextButton(onClick = {
                navController.navigateUp()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Already have an account? Login")
            }
            Spacer(Modifier.height(10.dp))


            MyButton(
                text = "Signup", showProgress = loading
            ) {
                val pair = HelperClass.validateUserCredentials(
                    userName = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )


                if (pair.first) {
                    val signupRequest = SignupRequest(name, email, password)
                    authViewmodel.signup(signupRequest)

                } else {
                   pair.second.toast(context)
                }

            }


        }
    }

}