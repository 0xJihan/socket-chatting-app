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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.jihan.app.domain.networking.SocketHandler
import com.jihan.app.domain.utils.Constants.TAG
import com.jihan.app.ui.theme.AppTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        SocketHandler.setSocket("This is a demo socket")
        SocketHandler.establishConnection()


        setContent {
            AppTheme {
                CenterBox {

                    var text by remember { mutableStateOf("Hello World") }

                    Text(text, fontSize = 25.sp)


                    LaunchedEffect(Unit) {
                        SocketHandler.getSocket().on("msg"){
                            text = it[0].toString()
                        }
                    }

                }
            }
        }
    }

}



@Composable
fun CenterBox(modifier: Modifier = Modifier.fillMaxSize(),content : @Composable ColumnScope.() -> Unit) {
   Column(modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {content()}
}

