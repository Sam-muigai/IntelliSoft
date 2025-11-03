package com.samkt.intellisoft.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.intellisoft.domain.model.User
import com.samkt.intellisoft.domain.repositories.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.getUser()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            User()
        )

}

fun getInitials(name: String): String {
    val words = name.split(" ")
    return if (words.size >= 2) {
        "${words[0].first()}${words[1].first()}".uppercase()
    } else {
        name.take(2).uppercase()
    }
}