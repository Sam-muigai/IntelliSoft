package com.samkt.intellisoft.utils

sealed interface OneTimeEvents {
    data class ShowMessage(val message: String) : OneTimeEvents
    data class Navigate(val route: Any) : OneTimeEvents
    data object PopBackStack : OneTimeEvents
}