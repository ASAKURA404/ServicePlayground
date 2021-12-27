package com.inspiration.serviceplayground

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inspiration.serviceplayground.ui.theme.ServicePlaygroundTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val sVM : SViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServicePlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ConversationScreen(sVM = sVM, name = "Android")
                }
            }
        }
    }


    @Composable
    fun ConversationScreen(sVM : SViewModel, name: String) {
        val status : Boolean by sVM.status.observeAsState(false)
        Greeting(name = name, status = status)
    }

    @Composable
    fun Greeting(name: String, status : Boolean) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello $name!",
                fontSize = 30.sp,
                modifier = Modifier.padding(3.dp))
            ForeGroundServiceButton(status)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ForeGroundServiceButton(status : Boolean) {
        var isClick by remember { mutableStateOf(false)}
        Button(
            onClick = { isClick = !isClick },
            modifier = Modifier
                .border(1.dp, Color.Red)
                .padding(3.dp)
        )
        {
            Text(
                text = "ForeGroundServiceButton",
                fontSize = 25.sp
            )
        }

        LaunchedEffect(key1 = isClick, block = {
            if (isClick) {
                serviceStart()
            }
        } )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun serviceStart() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        startForegroundService(serviceIntent)
        withContext(Dispatchers.Main) {Toast.makeText(this@MainActivity, "hello", Toast.LENGTH_SHORT).show() }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ServicePlaygroundTheme {
            Greeting("Android", false)
        }
    }
}
