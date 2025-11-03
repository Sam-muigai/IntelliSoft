package com.samkt.intellisoft.features.navigation

sealed class Screens(val route: String) {
    data object Auth : Screens("auth")
    data object Login : Screens("login")
    data object SignUp : Screens("sign_up")
    data object Home : Screens("home")
    data object AddNewPatient : Screens("add_new_patient")
}