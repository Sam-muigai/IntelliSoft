package com.samkt.intellisoft.features.vitals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.intellisoft.domain.model.Vitals
import com.samkt.intellisoft.domain.repositories.PatientRepository
import com.samkt.intellisoft.features.navigation.NavArguments
import com.samkt.intellisoft.features.navigation.Screens
import com.samkt.intellisoft.utils.OneTimeEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class VitalsScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _vitalsScreenState = MutableStateFlow(VitalsScreenState())
    val vitalsScreenState = _vitalsScreenState.asStateFlow()

    init {
        val patientId = savedStateHandle.get<Int>(NavArguments.PATIENT_ID) ?: 0
        _vitalsScreenState.update {
            it.copy(patientId = patientId)
        }
        viewModelScope.launch {
            patientRepository.getPatient(patientId).collect { patient ->
                _vitalsScreenState.update {
                    it.copy(patientName = "${patient?.firstName} ${patient?.lastName}")
                }
            }
        }
    }

    private val _oneTimeEvents = Channel<OneTimeEvents>()
    val oneTimeEvents = _oneTimeEvents.receiveAsFlow()


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
            viewModelScope.launch {
                val vitals = Vitals(
                    id = vitalsId,
                    height = height,
                    weight = weight,
                    patientId = patientId,
                    visitDate = LocalDate.parse(visitDate),
                )
               val vitalsId =  patientRepository.saveVitalsInformation(vitals)
                _vitalsScreenState.update {
                    it.copy(vitalsId = vitalsId)
                }
                _oneTimeEvents.trySend(
                    OneTimeEvents.Navigate(
                        Screens.Assessment.createRoute(
                            bmi,
                            vitalsScreenState.value.patientId
                        )
                    )
                )
            }
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
    val bmi: String = "",
    val patientId: Int = 0,
    val vitalsId: Int = 0
)

sealed interface VitalsScreenEvent {
    data class OnVisitDateChange(val date: String) : VitalsScreenEvent
    data class OnHeightChange(val height: String) : VitalsScreenEvent
    data class OnWeightChange(val weight: String) : VitalsScreenEvent
    data object OnSaveVitals : VitalsScreenEvent
}