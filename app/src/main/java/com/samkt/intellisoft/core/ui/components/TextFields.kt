package com.samkt.intellisoft.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.samkt.intellisoft.R

@Composable
fun TibaTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String = "",
    label: String? = null,
    errorMessage: String? = null
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
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
        )
        AnimatedVisibility(
            errorMessage != null
        ) {
            Column {
                Spacer(Modifier.height(4.dp))
                errorMessage?.let {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error
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
    errorMessage: String? = null
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
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
            errorMessage != null
        ) {
            Column {
                Spacer(Modifier.height(4.dp))
                errorMessage?.let {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error
                        ),
                    )
                }
            }
        }
    }
}