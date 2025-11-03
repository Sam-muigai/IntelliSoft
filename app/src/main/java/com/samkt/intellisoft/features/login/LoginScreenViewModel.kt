package com.samkt.intellisoft.features.login

import androidx.lifecycle.ViewModel
import com.samkt.intellisoft.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()


    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnEmailChange -> {
                _loginScreenState.update {
                    it.copy(email = event.email)
                }
            }

            LoginScreenEvent.OnLoginButtonClick -> {
                loginUser()
            }

            is LoginScreenEvent.OnPasswordChange -> {
                _loginScreenState.update {
                    it.copy(password = event.password)
                }
            }

            LoginScreenEvent.OnPasswordVisibilityChange -> {
                _loginScreenState.update {
                    it.copy(
                        passwordVisible = !loginScreenState.value.passwordVisible
                    )
                }
            }
        }
    }

    private fun loginUser() {
        clearErrorState()
        loginScreenState.value.apply {
            when {
                email.isEmpty() -> {
                    _loginScreenState.update {
                        it.copy(emailError = "Email cannot be empty")
                    }
                    return@apply
                }

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    _loginScreenState.update {
                        it.copy(emailError = "Please enter a valid email address")
                    }
                    return@apply
                }

                password.isEmpty() -> {
                    _loginScreenState.update {
                        it.copy(passwordError = "Password cannot be empty")
                    }
                    return@apply
                }

                password.length < 8 -> {
                    _loginScreenState.update {
                        it.copy(
                            passwordError = "Password must be at least 8 characters"
                        )
                    }
                    return@apply
                }
            }
        }
    }

    private fun clearErrorState(){
        _loginScreenState.update {
            it.copy(
                emailError = null,
                passwordError = null
            )
        }
    }
}

data class LoginScreenState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordVisible: Boolean = false,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
)

sealed class LoginScreenEvent {
    data class OnEmailChange(val email: String) : LoginScreenEvent()
    data class OnPasswordChange(val password: String) : LoginScreenEvent()
    data object OnPasswordVisibilityChange : LoginScreenEvent()
    data object OnLoginButtonClick : LoginScreenEvent()
}