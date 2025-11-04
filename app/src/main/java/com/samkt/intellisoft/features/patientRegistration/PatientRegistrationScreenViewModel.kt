package com.samkt.intellisoft.features.patientRegistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.repositories.PatientRepository
import com.samkt.intellisoft.features.navigation.Screens
import com.samkt.intellisoft.utils.OneTimeEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class PatientRegistrationScreenViewModel(
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _addPatientScreenState = MutableStateFlow(AddPatientScreenState())
    val addPatientScreenState = _addPatientScreenState.asStateFlow()


    private val _oneTimeEvent = Channel<OneTimeEvents>()
    val oneTimeEvents = _oneTimeEvent.receiveAsFlow()

    fun onEvent(
        event: AddPatientScreenEvent
    ) {
        when (event) {
            is AddPatientScreenEvent.OnPatientNumberChange -> {
                _addPatientScreenState.update {
                    it.copy(
                        patientNumber = event.number
                    )
                }
            }

            is AddPatientScreenEvent.OnFirstNameChange -> {
                _addPatientScreenState.update {
                    it.copy(
                        firstName = event.firstName
                    )
                }
            }

            is AddPatientScreenEvent.OnLastNameChange -> {
                _addPatientScreenState.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }

            is AddPatientScreenEvent.OnDobChange -> {
                _addPatientScreenState.update {
                    it.copy(
                        dob = event.dob
                    )
                }
            }

            is AddPatientScreenEvent.OnGenderChange -> {
                _addPatientScreenState.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }

            AddPatientScreenEvent.OnSavePatientClicked -> {
                // Save to db
                saveToDb()
            }

            is AddPatientScreenEvent.OnRegistrationDateChange -> {
                _addPatientScreenState.update {
                    it.copy(
                        registrationDate = event.date
                    )
                }
            }
        }
    }

    private fun saveToDb() {
        clearErrorState()
        addPatientScreenState.value.apply {
            when {
                patientNumber.isBlank() -> {
                    _addPatientScreenState.update {
                        it.copy(patientNumberError = "Patient number is required")
                    }
                    return@apply
                }

                registrationDate.isBlank() -> {
                    _addPatientScreenState.update {
                        it.copy(registrationDateError = "Registration date is required")
                    }
                    return@apply
                }

                firstName.isBlank() -> {
                    _addPatientScreenState.update {
                        it.copy(firstNameError = "First name is required")
                    }
                    return@apply
                }

                lastName.isBlank() -> {
                    _addPatientScreenState.update {
                        it.copy(lastNameError = "Last name is required")
                    }
                    return@apply
                }

                dob.isBlank() -> {
                    _addPatientScreenState.update {
                        it.copy(dobError = "Date of birth is required")
                    }
                    return@apply
                }

                gender.isBlank() -> {
                    _addPatientScreenState.update {
                        it.copy(genderError = "Gender is required")
                    }
                    return@apply
                }
            }
            viewModelScope.launch {
                val patient = Patient(
                    patientNumber = patientNumber,
                    registrationDate = LocalDate.parse(registrationDate),
                    firstName = firstName,
                    lastName = lastName,
                    dateOfBirth = LocalDate.parse(registrationDate),
                    gender = gender
                )
                val patientId = patientRepository.savePatient(patient)
                _oneTimeEvent.send(OneTimeEvents.Navigate(Screens.Vitals.createRoute(patientId)))
            }
        }
    }

    private fun clearErrorState() {
        _addPatientScreenState.update {
            it.copy(
                patientNumberError = null,
                registrationDateError = null,
                firstNameError = null,
                lastNameError = null,
                dobError = null,
                genderError = null
            )
        }
    }
}


data class AddPatientScreenState(
    val patientNumber: String = "",
    val patientNumberError: String? = null,
    val registrationDate: String = "",
    val registrationDateError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val dob: String = "",
    val dobError: String? = null,
    val gender: String = "",
    val genderError: String? = null,
)

sealed interface AddPatientScreenEvent {
    data class OnPatientNumberChange(val number: String) : AddPatientScreenEvent
    data class OnFirstNameChange(val firstName: String) : AddPatientScreenEvent
    data class OnLastNameChange(val lastName: String) : AddPatientScreenEvent
    data class OnDobChange(val dob: String) : AddPatientScreenEvent
    data class OnGenderChange(val gender: String) : AddPatientScreenEvent
    data class OnRegistrationDateChange(val date: String) : AddPatientScreenEvent
    data object OnSavePatientClicked : AddPatientScreenEvent
}