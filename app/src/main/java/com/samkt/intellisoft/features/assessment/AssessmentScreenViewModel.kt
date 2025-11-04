package com.samkt.intellisoft.features.assessment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.intellisoft.domain.model.Assessment
import com.samkt.intellisoft.domain.repositories.PatientRepository
import com.samkt.intellisoft.features.navigation.NavArguments
import com.samkt.intellisoft.utils.OneTimeEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class AssessmentScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _assessmentScreenState = MutableStateFlow(AssessmentScreenState())
    val assessmentScreenState = _assessmentScreenState.asStateFlow()

    init {
        val patientId = savedStateHandle.get<Int>(NavArguments.PATIENT_ID) ?: 0
        val vitalsId = savedStateHandle.get<Int>(NavArguments.VITALS_ID) ?: 0
        viewModelScope.launch {
            patientRepository.getPatient(patientId).collect { patient ->
                _assessmentScreenState.update {
                    it.copy(
                        patientName = "${patient?.firstName} ${patient?.lastName}",
                        patientId = patientId,
                        vitalsId = vitalsId
                    )
                }
            }
        }
    }


    private val _oneTimeEvents = Channel<OneTimeEvents>()
    val oneTimeEvents = _oneTimeEvents.receiveAsFlow()

    fun onEvent(event: AssessmentScreenEvent) {
        when (event) {
            is AssessmentScreenEvent.OnVisitDateChange -> {
                _assessmentScreenState.update {
                    it.copy(visitDate = event.date)
                }
            }

            is AssessmentScreenEvent.OnCommentChange -> {
                _assessmentScreenState.update {
                    it.copy(comment = event.comment)
                }
            }

            is AssessmentScreenEvent.OnGeneralHealthChange -> {
                _assessmentScreenState.update {
                    it.copy(generalHealth = event.health)
                }
            }

            is AssessmentScreenEvent.OnDietToLoseWeightChange -> {
                _assessmentScreenState.update {
                    it.copy(onDietToLoseWeight = event.isOnDiet)
                }
            }

            is AssessmentScreenEvent.OnCurrentlyTakingDrugsChange -> {
                _assessmentScreenState.update {
                    it.copy(currentlyTakingDrugs = event.isCurrentlyTakingDrugs)
                }
            }

            is AssessmentScreenEvent.OnSubmit -> {
                saveAssessment()
            }
        }
    }

    private fun saveAssessment() {
        assessmentScreenState.value.apply {
            when {
                visitDate.isEmpty() -> {
                    _assessmentScreenState.update {
                        it.copy(
                            visitDateError = "Visit date cannot be empty"
                        )
                    }
                    return@apply
                }
            }
            viewModelScope.launch {
                val assessment = Assessment(
                    id = assessmentId,
                    generalHealth = generalHealth,
                    onDiet = onDietToLoseWeight,
                    onDrugs = currentlyTakingDrugs,
                    comments = comment,
                    visitDate = LocalDate.parse(visitDate),
                    vitalId = vitalsId,
                )
                patientRepository.saveAssessment(assessment)
                _oneTimeEvents.send(OneTimeEvents.PopBackStack)
            }
        }
    }
}


data class AssessmentScreenState(
    val isLoading: Boolean = false,
    val patientName: String = "",
    val visitDate: String = "",
    val visitDateError: String? = null,
    val generalHealth: String = "Good",
    val onDietToLoseWeight: String = "Yes",
    val currentlyTakingDrugs: String = "Yes",
    val comment: String = "",
    val isOverweight: Boolean = false,
    val patientId: Int = 0,
    val vitalsId: Int = 0,
    val assessmentId: Int = 0
)

sealed interface AssessmentScreenEvent {
    data class OnVisitDateChange(val date: String) : AssessmentScreenEvent
    data class OnGeneralHealthChange(val health: String) : AssessmentScreenEvent
    data class OnCommentChange(val comment: String) : AssessmentScreenEvent
    data class OnDietToLoseWeightChange(val isOnDiet: String) : AssessmentScreenEvent
    data class OnCurrentlyTakingDrugsChange(val isCurrentlyTakingDrugs: String) :
        AssessmentScreenEvent

    data object OnSubmit : AssessmentScreenEvent
}