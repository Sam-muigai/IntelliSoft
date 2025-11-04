package com.samkt.intellisoft.features.login

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
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = koinViewModel(),
    onSignUpClick: () -> Unit,
    onSignInSuccess: (route: String) -> Unit = {}
) {
    val loginScreenState = loginScreenViewModel.loginScreenState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loginScreenViewModel.oneTimeEvents.collectLatest { event ->
            when (event) {
                is OneTimeEvents.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is OneTimeEvents.Navigate -> {
                    onSignInSuccess(event.route)
                }

                else -> Unit
            }
        }
    }
    LoginScreenContent(
        loginScreenState = loginScreenState,
        onEvent = loginScreenViewModel::onEvent,
        onSignUpClick = onSignUpClick
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    loginScreenState: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit,
    onSignUpClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(180.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "IntelliSoft",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            TibaTextField(
                value = loginScreenState.email,
                onValueChange = {
                    onEvent(LoginScreenEvent.OnEmailChange(it))
                },
                placeHolder = "example@gmail.com",
                label = "Email",
                errorMessage = loginScreenState.emailError
            )
            TibaPasswordTextField(
                value = loginScreenState.password,
                onValueChange = {
                    onEvent(LoginScreenEvent.OnPasswordChange(it))
                },
                label = "Password",
                placeHolder = "********",
                isPasswordVisible = loginScreenState.passwordVisible,
                onIconButtonClicked = {
                    onEvent(LoginScreenEvent.OnPasswordVisibilityChange)
                },
                errorMessage = loginScreenState.passwordError
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AnimatedContent(
                    targetState = loginScreenState.isLoading
                ) { isLoading ->
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp)
                        )
                    } else {
                        TibaFilledButton(
                            modifier = Modifier.fillMaxWidth(),
                            label = "LOGIN",
                            onClick = {
                                onEvent(LoginScreenEvent.OnLoginButtonClick)
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Are you new here? ",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable(onClick = onSignUpClick)
                )
            }
        }
    }
}