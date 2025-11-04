package com.samkt.intellisoft.core.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.samkt.intellisoft.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun TibaTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String = "",
    label: String? = null,
    errorMessage: String? = null,
    enabled: Boolean = true,
    minLines: Int = 1,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                ),
            )
            Spacer(Modifier.height(8.dp))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    text = placeHolder,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            enabled = enabled,
            readOnly = !enabled,
            minLines = minLines,
        )
        AnimatedVisibility(
            errorMessage != null,
        ) {
            Column {
                Spacer(Modifier.height(4.dp))
                errorMessage?.let {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun TibaPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String = "",
    label: String? = null,
    onIconButtonClicked: () -> Unit,
    isPasswordVisible: Boolean,
    errorMessage: String? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                ),
            )
            Spacer(Modifier.height(8.dp))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.bodyMedium,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = placeHolder,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onIconButtonClicked,
                ) {
                    AnimatedContent(isPasswordVisible) {
                        if (it) {
                            Icon(
                                painter = painterResource(id = R.drawable.eye_closed),
                                contentDescription = null,
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.eye_open),
                                contentDescription = null,
                            )
                        }
                    }
                }
            },
        )
        AnimatedVisibility(
            errorMessage != null,
        ) {
            Column {
                Spacer(Modifier.height(4.dp))
                errorMessage?.let {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error,
                        ),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TibaDropDown(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    items: List<String>,
    placeHolder: String = "",
    label: String? = null,
    errorMessage: String? = null,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                ),
            )
            Spacer(Modifier.height(8.dp))
        }

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = value,
                onValueChange = {},
                readOnly = true,
                shape = MaterialTheme.shapes.medium,
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = placeHolder,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        onClick = {
                            onValueChange(item)
                            isExpanded = false
                        },
                    )
                }
            }
        }

        AnimatedVisibility(
            errorMessage != null,
        ) {
            Column {
                Spacer(Modifier.height(4.dp))
                errorMessage?.let {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error,
                        ),
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TibaDatePicker(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String = "",
    label: String? = null,
    errorMessage: String? = null,
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                ),
            )
            Spacer(Modifier.height(8.dp))
        }

        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = {},
                readOnly = true,
                shape = MaterialTheme.shapes.medium,
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = placeHolder,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Date",
                    )
                },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { isDialogOpen = true },
                    ),
            )
        }

        AnimatedVisibility(
            errorMessage != null,
        ) {
            Column {
                Spacer(Modifier.height(4.dp))
                errorMessage?.let {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error,
                        ),
                    )
                }
            }
        }
    }

    if (isDialogOpen) {
        DatePickerDialog(
            onDismissRequest = { isDialogOpen = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        isDialogOpen = false
                        // Get selected date, format it, and pass to callback
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            val formattedDate =
                                date.format(DateTimeFormatter.ISO_LOCAL_DATE) // yyyy-MM-dd
                            onValueChange(formattedDate)
                        }
                    },
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isDialogOpen = false },
                ) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
