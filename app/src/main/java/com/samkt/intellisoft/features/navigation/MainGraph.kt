package com.samkt.intellisoft.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.samkt.intellisoft.features.patientRegistration.PatientRegistrationScreen
import com.samkt.intellisoft.features.home.HomeScreen
import com.samkt.intellisoft.features.login.LoginScreen
import com.samkt.intellisoft.features.signUp.SignUpScreen
import com.samkt.intellisoft.features.vitals.VitalsScreen


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
            PatientRegistrationScreen(
                onBackClick = {
                    navHostController.popBackStack()
                },
                onNavigate = { route ->
                    navHostController.navigate(route)
                }
            )
        }

        composable(
            route = Screens.Vitals.route
        ) {
            VitalsScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}