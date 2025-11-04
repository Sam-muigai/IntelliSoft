package com.samkt.intellisoft.features.vitals

import androidx.lifecycle.ViewModel
import com.samkt.intellisoft.features.navigation.Screens
import com.samkt.intellisoft.utils.OneTimeEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class VitalsScreenViewModel : ViewModel() {

    private val _vitalsScreenState = MutableStateFlow(VitalsScreenState())
    val vitalsScreenState = _vitalsScreenState.asStateFlow()


    private val _oneTimeEvents = Channel<OneTimeEvents>()
    val oneTimeEvents = _oneTimeEvents.receiveAsFlow()


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
                calculateBmi()
            }

            is VitalsScreenEvent.OnWeightChange -> {
                val digitsOnly = event.weight.filter { it.isDigit() }
                _vitalsScreenState.update {
                    it.copy(weight = digitsOnly)
                }
                calculateBmi()
            }

            is VitalsScreenEvent.OnVisitDateChange -> {
                _vitalsScreenState.update {
                    it.copy(visitDate = event.date)
                }
            }

            is VitalsScreenEvent.OnSaveVitals -> {
                saveVitals()
            }
        }
    }

    private fun saveVitals() {
        vitalsScreenState.value.apply {
            when {
                visitDate.isEmpty() -> {
                    _vitalsScreenState.update {
                        it.copy(visitDateError = "Please provide a visit date")
                    }
                    return@apply
                }

                height.isEmpty() -> {
                    _vitalsScreenState.update {
                        it.copy(heightError = "Please provide a height")
                    }
                    return@apply
                }

                weight.isEmpty() -> {
                    _vitalsScreenState.update {
                        it.copy(weightError = "Please provide a weight")
                    }
                    return@apply
                }
            }
            _oneTimeEvents.trySend(OneTimeEvents.Navigate(Screens.Assessment.createRoute(bmi)))
        }
    }

    private fun calculateBmi() {
        val heightInCm = vitalsScreenState.value.height.toDoubleOrNull() ?: 0.0
        val weightInKg = vitalsScreenState.value.weight.toDoubleOrNull() ?: 0.0
        val heightInMeters = heightInCm / 100
        try {
            val bmi = weightInKg / (heightInMeters * heightInMeters)
            _vitalsScreenState.update {
                it.copy(
                    bmi = String.format("%.2f", bmi)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
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