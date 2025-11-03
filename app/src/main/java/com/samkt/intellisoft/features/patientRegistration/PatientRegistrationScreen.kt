package com.samkt.intellisoft.features.patientRegistration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samkt.intellisoft.core.ui.components.TibaDatePicker
import com.samkt.intellisoft.core.ui.components.TibaDropDown
import com.samkt.intellisoft.core.ui.components.TibaFilledButton
import com.samkt.intellisoft.core.ui.components.TibaTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun PatientRegistrationScreen(
    patientRegistrationScreenViewModel: PatientRegistrationScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val addPatientScreenState =
        patientRegistrationScreenViewModel.addPatientScreenState.collectAsStateWithLifecycle().value

    PatientRegistrationScreenContent(
        addPatientScreenState = addPatientScreenState,
        onEvent = patientRegistrationScreenViewModel::onEvent,
        onBackClick = onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientRegistrationScreenContent(
    modifier: Modifier = Modifier,
    addPatientScreenState: AddPatientScreenState,
    onEvent: (AddPatientScreenEvent) -> Unit,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = "PATIENT REGISTRATION FORM",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TibaTextField(
                value = addPatientScreenState.patientNumber,
                onValueChange = {
                    onEvent(AddPatientScreenEvent.OnPatientNumberChange(it))
                },
                label = "Patient's Number",
                errorMessage = addPatientScreenState.patientNumberError
            )

            TibaDatePicker(
                value = addPatientScreenState.registrationDate,
                onValueChange = {
                    onEvent(AddPatientScreenEvent.OnRegistrationDateChange(it))
                },
                label = "Registration Date",
                errorMessage = addPatientScreenState.registrationDateError,
                placeHolder = "Registration Date"
            )


            TibaTextField(
                value = addPatientScreenState.firstName,
                onValueChange = {
                    onEvent(AddPatientScreenEvent.OnFirstNameChange(it))
                },
                label = "First Name",
                errorMessage = addPatientScreenState.firstNameError
            )

            TibaTextField(
                value = addPatientScreenState.lastName,
                onValueChange = {
                    onEvent(AddPatientScreenEvent.OnLastNameChange(it))
                },
                label = "Last Name",
                errorMessage = addPatientScreenState.lastNameError
            )

            TibaDatePicker(
                value = addPatientScreenState.dob,
                onValueChange = {
                    onEvent(AddPatientScreenEvent.OnDobChange(it))
                },
                label = "Date of Birth",
                errorMessage = addPatientScreenState.dobError,
                placeHolder = "Select Date of Birth"
            )

            TibaDropDown(
                value = addPatientScreenState.gender,
                items = listOf("Male", "Female"),
                onValueChange = {
                    onEvent(AddPatientScreenEvent.OnGenderChange(it))
                },
                label = "Gender",
                placeHolder = "Select Gender",
                errorMessage = addPatientScreenState.genderError
            )

            TibaFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = "SAVE",
                onClick = {
                    onEvent(AddPatientScreenEvent.OnSavePatientClicked)
                }
            )
        }
    }
}