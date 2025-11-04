package com.samkt.intellisoft.features.assessment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
fun AssessmentScreen(
    assessmentScreenViewModel: AssessmentScreenViewModel = koinViewModel(),
    bmi: Double,
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    val assessmentScreenState =
        assessmentScreenViewModel.assessmentScreenState.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        assessmentScreenViewModel.oneTimeEvents.collectLatest { event ->
            when (event) {
                OneTimeEvents.PopBackStack -> {
                    onSaveSuccess()
                }

                else -> Unit
            }
        }
    }
    AssessmentScreenContent(
        isOverweight = bmi >= 25,
        onBackClick = onBackClick,
        assessmentScreenState = assessmentScreenState,
        onEvent = assessmentScreenViewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreenContent(
    modifier: Modifier = Modifier,
    isOverweight: Boolean,
    onBackClick: () -> Unit = {},
    assessmentScreenState: AssessmentScreenState,
    onEvent: (AssessmentScreenEvent) -> Unit = {}
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
                        text = "ASSESSMENT FORM",
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
                value = assessmentScreenState.patientName,
                onValueChange = {},
                label = "Patient's Name",
                enabled = false
            )
            TibaDatePicker(
                value = assessmentScreenState.visitDate,
                onValueChange = {
                    onEvent(AssessmentScreenEvent.OnVisitDateChange(it))
                },
                label = "Visit Date",
                placeHolder = "Select Visit Date",
                errorMessage = assessmentScreenState.visitDateError
            )
            Text(
                text = "General Health",
                style = MaterialTheme.typography.bodyMedium
            )
            Column {
                listOf("Good", "Poor").forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = assessmentScreenState.generalHealth == it,
                            onClick = {
                                onEvent(AssessmentScreenEvent.OnGeneralHealthChange(it))
                            },
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            if (isOverweight) {
                Text(
                    text = "Are you currently taking any drugs?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Column {
                    listOf("Yes", "No").forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = assessmentScreenState.currentlyTakingDrugs == it,
                                onClick = {
                                    onEvent(AssessmentScreenEvent.OnCurrentlyTakingDrugsChange(it))
                                },
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "Have you ever been on a diet to lose weight?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Column {
                    listOf("Yes", "No").forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = assessmentScreenState.onDietToLoseWeight == it,
                                onClick = {
                                    onEvent(AssessmentScreenEvent.OnDietToLoseWeightChange(it))
                                },
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            TibaTextField(
                value = assessmentScreenState.comment,
                onValueChange = {
                    onEvent(AssessmentScreenEvent.OnCommentChange(it))
                },
                label = "Comments",
                minLines = 4
            )
            TibaFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = {
                    onEvent(AssessmentScreenEvent.OnSubmit)
                },
                label = "SAVE"
            )
        }
    }
}