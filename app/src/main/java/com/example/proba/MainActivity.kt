package com.example.proba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                LoginPage(tokenManager = tokenManager)
                //MainNavHost()
            }
        }
    }
}
