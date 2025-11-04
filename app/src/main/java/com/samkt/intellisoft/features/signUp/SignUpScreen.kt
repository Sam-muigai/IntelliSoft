package com.samkt.intellisoft.features.signUp

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samkt.intellisoft.R
import com.samkt.intellisoft.core.ui.components.TibaFilledButton
import com.samkt.intellisoft.core.ui.components.TibaPasswordTextField
import com.samkt.intellisoft.core.ui.components.TibaTextField
import com.samkt.intellisoft.utils.OneTimeEvents
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = koinViewModel(),
    onSignInClick: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {},
) {
    val signUpScreenState = signUpViewModel.signUpScreenState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        signUpViewModel.oneTimeEvents.collectLatest { event ->
            when (event) {
                is OneTimeEvents.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is OneTimeEvents.Navigate -> {
                    onSignUpSuccess()
                }

                else -> Unit
            }
        }
    }

    SignUpScreenContent(
        signUpScreenState = signUpScreenState,
        onEvent = signUpViewModel::onEvent,
        onSignInClick = onSignInClick,
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    signUpScreenState: SignUpScreenState,
    onEvent: (SignUpScreenEvent) -> Unit,
    onSignInClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(180.dp),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = "IntelliSoft",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
            TibaTextField(
                value = signUpScreenState.email,
                onValueChange = {
                    onEvent(SignUpScreenEvent.OnEmailChange(it))
                },
                label = "Email",
                placeHolder = "example@gmail.com",
                errorMessage = signUpScreenState.emailError,
            )
            TibaTextField(
                value = signUpScreenState.firstName,
                onValueChange = {
                    onEvent(SignUpScreenEvent.OnFirstNameChange(it))
                },
                label = "First Name",
                placeHolder = "Enter your first name",
                errorMessage = signUpScreenState.firstNameError,
            )
            TibaTextField(
                value = signUpScreenState.lastName,
                onValueChange = {
                    onEvent(SignUpScreenEvent.OnLastNameChange(it))
                },
                label = "Last Name",
                placeHolder = "Enter your last name",
                errorMessage = signUpScreenState.lastNameError,
            )
            TibaPasswordTextField(
                value = signUpScreenState.password,
                onValueChange = {
                    onEvent(SignUpScreenEvent.OnPasswordChange(it))
                },
                label = "Password",
                placeHolder = "********",
                isPasswordVisible = signUpScreenState.passwordVisible,
                onIconButtonClicked = {
                    onEvent(SignUpScreenEvent.OnPasswordVisibilityChange)
                },
                errorMessage = signUpScreenState.passwordError,
            )
            TibaPasswordTextField(
                value = signUpScreenState.confirmPassword,
                onValueChange = {
                    onEvent(SignUpScreenEvent.OnConfirmPasswordChange(it))
                },
                label = "Confirm Password",
                placeHolder = "********",
                isPasswordVisible = signUpScreenState.confirmPasswordVisible,
                onIconButtonClicked = {
                    onEvent(SignUpScreenEvent.OnConfirmPasswordVisibilityChange)
                },
                errorMessage = signUpScreenState.confirmPasswordError,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                AnimatedContent(
                    targetState = signUpScreenState.isLoading,
                ) { isLoading ->
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                        )
                    } else {
                        TibaFilledButton(
                            modifier = Modifier.fillMaxWidth(),
                            label = "SIGN UP",
                            onClick = {
                                onEvent(SignUpScreenEvent.OnSignUpClick)
                            },
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Sign In",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable(onClick = onSignInClick),
                )
            }
        }
    }
}
