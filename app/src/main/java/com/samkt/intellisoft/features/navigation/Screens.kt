package com.samkt.intellisoft.features.navigation

sealed class Screens(val route: String) {
    data object Auth : Screens("auth")
    data object Login : Screens("login")
    data object SignUp : Screens("sign_up")
    data object Home : Screens("home")
    data object AddNewPatient : Screens("add_new_patient")
    data object AddNewPatientInfo : Screens("add_new_patient_info")

    data object Vitals : Screens("vitals/{${NavArguments.PATIENT_ID}}") {
        fun createRoute(patientId: Int): String {
            return "vitals/$patientId"
        }
    }

    data object Assessment :
        Screens("assessment/{${NavArguments.BMI}}/{${NavArguments.PATIENT_ID}}") {
        fun createRoute(bmi: String, patientId: Int): String {
            return "assessment/$bmi/$patientId"
        }
    }
}

object NavArguments {
    const val BMI = "bmi"
    const val PATIENT_ID = "patient_id"
}