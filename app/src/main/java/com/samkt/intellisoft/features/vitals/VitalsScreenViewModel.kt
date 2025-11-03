package com.samkt.intellisoft.features.vitals

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VitalsScreenViewModel : ViewModel() {

    private val _vitalsScreenState = MutableStateFlow(VitalsScreenState())
    val vitalsScreenState = _vitalsScreenState.asStateFlow()


    init {
        _vitalsScreenState.update {
            it.copy(patientName = "John Doe")
        }
    }

    fun onEvent(event: VitalsScreenEvent) {
        when (event) {
            is VitalsScreenEvent.OnHeightChange -> {
                val digitsOnly = event.height.filter { it.isDigit() }
                _vitalsScreenState.update {
                    it.copy(height = digitsOnly)
                }
            }

            is VitalsScreenEvent.OnWeightChange -> {
                val digitsOnly = event.weight.filter { it.isDigit() }
                _vitalsScreenState.update {
                    it.copy(weight = digitsOnly)
                }
            }

            is VitalsScreenEvent.OnVisitDateChange -> {
                _vitalsScreenState.update {
                    it.copy(visitDate = event.date)
                }
            }
            is VitalsScreenEvent.OnSaveVitals -> {

            }
        }
    }

}

data class VitalsScreenState(
    val patientName: String = "",
    val visitDate: String = "",
    val visitDateError: String? = null,
    val height: String = "",
    val heightError: String? = null,
    val weight: String = "",
    val weightError: String? = null,
    val bmi: String = ""
)

sealed interface VitalsScreenEvent {
    data class OnVisitDateChange(val date: String) : VitalsScreenEvent
    data class OnHeightChange(val height: String) : VitalsScreenEvent
    data class OnWeightChange(val weight: String) : VitalsScreenEvent
    data object OnSaveVitals : VitalsScreenEvent
}