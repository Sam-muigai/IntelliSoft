package com.samkt.intellisoft.features.vitals

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samkt.intellisoft.core.ui.components.TibaDatePicker
import com.samkt.intellisoft.core.ui.components.TibaFilledButton
import com.samkt.intellisoft.core.ui.components.TibaTextField
import com.samkt.intellisoft.utils.OneTimeEvents
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun VitalsScreen(
    vitalsScreenViewModel: VitalsScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onNavigate: (route: String) -> Unit = {}
) {
    val vitalsScreenState =
        vitalsScreenViewModel.vitalsScreenState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        vitalsScreenViewModel.oneTimeEvents.collectLatest { event ->
            when (event) {
                is OneTimeEvents.Navigate -> {
                    onNavigate(event.route)
                }

                else -> Unit
            }
        }
    }

    VitalsScreenContent(
        onBackClick = onBackClick,
        vitalsScreenState = vitalsScreenState,
        onEvent = vitalsScreenViewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsScreenContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    vitalsScreenState: VitalsScreenState,
    onEvent: (VitalsScreenEvent) -> Unit
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
                        text = "PATIENT VITALS FORM",
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
                value = vitalsScreenState.patientName,
                onValueChange = {},
                label = "Patient's Name",
                enabled = false
            )

            TibaDatePicker(
                value = vitalsScreenState.visitDate,
                onValueChange = {
                    onEvent(VitalsScreenEvent.OnVisitDateChange(it))
                },
                label = "Visit Date",
                placeHolder = "Select Visit Date",
                errorMessage = vitalsScreenState.visitDateError
            )

            TibaTextField(
                value = vitalsScreenState.height,
                onValueChange = {
                    onEvent(VitalsScreenEvent.OnHeightChange(it))
                },
                label = "Height(In CM)",
                errorMessage = vitalsScreenState.heightError
            )
            TibaTextField(
                value = vitalsScreenState.weight,
                onValueChange = {
                    onEvent(VitalsScreenEvent.OnWeightChange(it))
                },
                label = "Weight(In KG)",
                errorMessage = vitalsScreenState.weightError
            )
            TibaTextField(
                value = vitalsScreenState.bmi,
                onValueChange = {},
                label = "BMI",
                enabled = false
            )
            TibaFilledButton(
                modifier = Modifier.fillMaxWidth(),
                label = "SAVE",
                onClick = {
                    onEvent(VitalsScreenEvent.OnSaveVitals)
                }
            )
        }
    }
}