package com.samkt.intellisoft.features.home

import android.icu.text.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.intellisoft.domain.model.User
import com.samkt.intellisoft.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeScreenViewModel(
    userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.getUser()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            User()
        )

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    fun onDateChange(date: String) {
        _homeScreenState.update {
            it.copy(
                date = date
            )
        }
    }

}


data class HomeScreenState(
    val date: String = getTodaysDate()
)


fun getInitials(name: String): String {
    val words = name.split(" ")
    return if (words.size >= 2) {
        "${words[0].first()}${words[1].first()}".uppercase()
    } else {
        name.take(2).uppercase()
    }
}

fun getTodaysDate(): String {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now())
}