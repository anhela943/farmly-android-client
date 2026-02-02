package com.example.proba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import com.example.proba.activity.login.LoginPage
import com.example.proba.data.remote.ApiClient
import com.example.proba.navigation.MainNavHost
import com.example.proba.ui.theme.ProbaTheme
import com.example.proba.util.TokenManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)
        ApiClient.init(tokenManager)

        setContent {
            ProbaTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    LoginPage(tokenManager = tokenManager)
                    //MainNavHost()
                }
            }
        }
    }
}
