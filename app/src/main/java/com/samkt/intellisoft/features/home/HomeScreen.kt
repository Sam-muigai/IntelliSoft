package com.samkt.intellisoft.features.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samkt.intellisoft.core.ui.components.TibaDatePicker
import com.samkt.intellisoft.core.ui.components.TibaFilledButton
import com.samkt.intellisoft.domain.model.User
import com.samkt.intellisoft.features.patientRegistration.AddPatientScreenEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = koinViewModel(),
    onAddPatientClick: () -> Unit = {}
) {
    val user = homeScreenViewModel.user.collectAsStateWithLifecycle().value
    val date = homeScreenViewModel.date
    val homeScreenUiState = homeScreenViewModel.homeScreenUiState.collectAsStateWithLifecycle().value
    HomeScreenContent(
        user = user,
        onAddPatientClick = onAddPatientClick,
        onDateChange = homeScreenViewModel::onDateChange,
        date = date,
        homeScreenUiState = homeScreenUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    user: User,
    onAddPatientClick: () -> Unit = {},
    onDateChange: (String) -> Unit = {},
    date: String = "",
    homeScreenUiState: HomeScreenUiState
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = getInitials(user.fullName),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = user.fullName,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            TibaFilledButton(
                onClick = onAddPatientClick,
                label = "+ ADD NEW PATIENT"
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
            Text(
                "Patient's listing",
                style = MaterialTheme.typography.titleMedium
            )
            TibaDatePicker(
                value = date,
                onValueChange = onDateChange,
                label = "Registration Date",
                placeHolder = "Choose a date"
            )
            AnimatedContent(homeScreenUiState) { state ->
                when (state) {
                    is HomeScreenUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(state.message)
                        }
                    }

                    HomeScreenUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is HomeScreenUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.visits) { visit ->
                                Text(visit.name)
                            }
                        }
                    }
                }
            }
        }
    }
}