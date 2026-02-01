package com.example.proba.activity.login

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proba.data.repository.AuthRepository
import com.example.proba.navigation.MainNavHost
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.TokenManager
import com.example.proba.viewmodel.LoginUiState
import com.example.proba.viewmodel.LoginViewModel

@Composable
fun LoginPage(tokenManager: TokenManager? = null) {
    val navController = rememberNavController()

    val authRepository = remember(tokenManager) {
        tokenManager?.let { AuthRepository(it) }
    }

    val loginViewModel = remember(authRepository) {
        authRepository?.let { LoginViewModel(it) }
    }

    val loginState by loginViewModel?.uiState?.collectAsState()
        ?: remember { androidx.compose.runtime.mutableStateOf(LoginUiState()) }

    val navigateToHome = remember(navController) {
        {
            navController.navigate(MainRoutes.Home) {
                popUpTo("welcome") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(loginState.isLoginSuccessful) {
        if (loginState.isLoginSuccessful) {
            Log.d("LoginPage", "Login successful, navigating to main screen...")
            navigateToHome()
        }
    }

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
                email = loginState.email,
                password = loginState.password,
                isLoading = loginState.isLoading,
                errorMessage = loginState.errorMessage,
                onEmailChange = { loginViewModel?.onEmailChange(it) },
                onPasswordChange = { loginViewModel?.onPasswordChange(it) },
                onLoginClick = {
                    if (loginViewModel == null) {
                        navigateToHome()
                    } else {
                        loginViewModel.login()
                    }
                },
                onSignUpClick = { navController.navigate("signup") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("signup") {
            SignUpScreen(
                onRegisterClick = { navigateToHome() },
                onLoginClick = { navController.navigate("login") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(MainRoutes.Home) {
            MainNavHost(
                startDestination = MainRoutes.Home,
                onLogout = {
                    navController.navigate("welcome") {
                        popUpTo(MainRoutes.Home) { inclusive = true }
                    }
                }
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
