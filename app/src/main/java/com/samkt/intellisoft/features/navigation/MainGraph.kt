package com.samkt.intellisoft.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.samkt.intellisoft.features.addPatient.AddPatientScreen
import com.samkt.intellisoft.features.home.HomeScreen
import com.samkt.intellisoft.features.login.LoginScreen
import com.samkt.intellisoft.features.signUp.SignUpScreen


@Composable
fun App() {
    val navHostController = rememberNavController()
    NavHost(
        startDestination = Screens.Auth.route,
        navController = navHostController
    ) {
        navigation(
            startDestination = Screens.Login.route,
            route = Screens.Auth.route
        ) {
            composable(
                route = Screens.Login.route
            ) {
                LoginScreen(
                    onSignUpClick = {
                        navHostController.navigate(Screens.SignUp.route)
                    },
                    onSignInSuccess = { homeRoute ->
                        navHostController.navigate(homeRoute) {
                            popUpTo(Screens.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(
                route = Screens.SignUp.route
            ) {
                SignUpScreen(
                    onSignInClick = {
                        navHostController.navigate(Screens.Login.route)
                    },
                )
            }
        }
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(
                onAddPatientClick = {
                    navHostController.navigate(Screens.AddNewPatient.route)
                }
            )
        }

        composable(
            route = Screens.AddNewPatient.route
        ){
            AddPatientScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}