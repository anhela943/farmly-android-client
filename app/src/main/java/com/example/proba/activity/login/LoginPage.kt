package com.example.proba.activity.login

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proba.data.remote.ApiClient
import com.example.proba.data.repository.AuthRepository
import com.example.proba.navigation.MainNavHost
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.TokenManager
import com.example.proba.viewmodel.LoginUiState
import com.example.proba.viewmodel.LoginViewModel
import com.example.proba.viewmodel.RegisterUiState
import com.example.proba.viewmodel.RegisterViewModel
import com.example.proba.activity.splash.OnboardingPager
import kotlinx.coroutines.launch

@Composable
fun LoginPage(tokenManager: TokenManager? = null) {
    val navController = rememberNavController()
    var startDestination by remember { mutableStateOf<String?>(null) }

    val authRepository = remember(tokenManager) {
        tokenManager?.let { AuthRepository(it) }
    }

    val loginViewModel = remember(authRepository) {
        authRepository?.let { LoginViewModel(it) }
    }

    val registerViewModel = remember(authRepository) {
        authRepository?.let { RegisterViewModel(it) }
    }

    val loginState by loginViewModel?.uiState?.collectAsState()
        ?: remember { androidx.compose.runtime.mutableStateOf(LoginUiState()) }

    val registerState by registerViewModel?.uiState?.collectAsState()
        ?: remember { androidx.compose.runtime.mutableStateOf(RegisterUiState()) }

    val coroutineScope = rememberCoroutineScope()

    val navigateToHome = remember(navController) {
        {
            navController.navigate(MainRoutes.Home) {
                popUpTo("welcome") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(Unit) {
        val onboardingCompleted = tokenManager?.isOnboardingCompleted() ?: true
        if (!onboardingCompleted) {
            startDestination = "onboarding"
        } else {
            val valid = tokenManager?.isTokenValid() ?: false
            if (valid) {
                startDestination = MainRoutes.Home
            } else {
                tokenManager?.clearToken()
                startDestination = "welcome"
            }
        }
    }

    LaunchedEffect(loginState.isLoginSuccessful) {
        if (loginState.isLoginSuccessful) {
            Log.d("LoginPage", "Login successful, navigating to main screen...")
            navigateToHome()
        }
    }

    LaunchedEffect(registerState.isRegisterSuccessful) {
        if (registerState.isRegisterSuccessful) {
            Log.d("LoginPage", "Registration successful, navigating to main screen...")
            navigateToHome()
        }
    }

    LaunchedEffect(Unit) {
        ApiClient.authFailureEvent.collect {
            navController.navigate("welcome") {
                popUpTo(MainRoutes.Home) { inclusive = true }
            }
        }
    }

    if (startDestination == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = startDestination!!
    ) {

        composable("onboarding") {
            OnboardingPager(
                onGetStartedClick = {
                    coroutineScope.launch {
                        tokenManager?.setOnboardingCompleted()
                    }
                    navController.navigate("welcome") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

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
                fullName = registerState.fullName,
                email = registerState.email,
                city = registerState.city,
                phoneNumber = registerState.phoneNumber,
                password = registerState.password,
                isLoading = registerState.isLoading,
                errorMessage = registerState.errorMessage,
                onFullNameChange = { registerViewModel?.onFullNameChange(it) },
                onEmailChange = { registerViewModel?.onEmailChange(it) },
                onCityChange = { registerViewModel?.onCityChange(it) },
                onPhoneNumberChange = { registerViewModel?.onPhoneNumberChange(it) },
                onPasswordChange = { registerViewModel?.onPasswordChange(it) },
                onRegisterClick = {
                    if (registerViewModel == null) {
                        navigateToHome()
                    } else {
                        registerViewModel.register()
                    }
                },
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
