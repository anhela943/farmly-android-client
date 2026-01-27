package com.example.proba.activity.login

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginPage() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            LoginWelcome(
                onLoginClick = {
                    navController.navigate("login")
                },
                onRegisterClick = {
                    navController.navigate("signup")
                }
            )
        }

        composable("login") {
            LoginScreen(
                onLoginClick = { },
                onSignUpClick = { navController.navigate("signup") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("signup") {
            SignUpScreen(
                onRegisterClick = { navController.navigate("login") },
                onLoginClick = { navController.navigate("login") },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun LoginPagePreview() {
    MaterialTheme {
        LoginPage()
    }
}
