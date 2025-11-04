package com.samkt.intellisoft.features.home

import android.icu.text.DateFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.intellisoft.domain.helpers.Result
import com.samkt.intellisoft.domain.model.User
import com.samkt.intellisoft.domain.model.Visit
import com.samkt.intellisoft.domain.repositories.PatientRepository
import com.samkt.intellisoft.domain.repositories.UserRepository
import com.samkt.intellisoft.utils.getTodaysDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeScreenViewModel(
    userRepository: UserRepository,
    private val patientRepository: PatientRepository
) : ViewModel() {

    val user = userRepository.getUser()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            User()
        )

    private val _homeScreenUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val homeScreenUiState = _homeScreenUiState.asStateFlow()

    var date by mutableStateOf(getTodaysDate())
        private set

    init {
        getPatients(getTodaysDate())
    }

    fun onDateChange(date: String) {
        this.date = date
        getPatients(date)
    }

    private fun getPatients(
        date: String
    ) {
        _homeScreenUiState.update { HomeScreenUiState.Loading }
        viewModelScope.launch {
            when (val result = patientRepository.getVisits(date)) {
                is Result.Error -> {
                    _homeScreenUiState.update { HomeScreenUiState.Error(result.message) }
                }

                is Result.Success -> {
                    _homeScreenUiState.update { HomeScreenUiState.Success(result.data) }
                }
            }
        }
    }

}


sealed interface HomeScreenUiState {
    data object Loading : HomeScreenUiState
    data class Success(val visits: List<Visit>) : HomeScreenUiState
    data class Error(val message: String) : HomeScreenUiState
}


fun getInitials(name: String): String {
    val words = name.split(" ")
    return if (words.size >= 2) {
        "${words[0].first()}${words[1].first()}".uppercase()
    } else {
        name.take(2).uppercase()
    }
}

