package com.samkt.intellisoft.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samkt.intellisoft.features.login.LoginScreen
import com.samkt.intellisoft.features.signUp.SignUpScreen


@Composable
fun App() {
    val navHostController = rememberNavController()
    NavHost(
        startDestination = Screen.Login.route,
        navController = navHostController
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(
                onSignUpClick = {
                    navHostController.navigate(Screen.SignUp.route)
                }
            )
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(
                onSignInClick = {
                    navHostController.navigate(Screen.Login.route)
                }
            )
        }
    }
}