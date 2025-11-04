package com.samkt.intellisoft.features.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.intellisoft.domain.helpers.Result
import com.samkt.intellisoft.domain.model.SignUp
import com.samkt.intellisoft.domain.repositories.AuthRepository
import com.samkt.intellisoft.features.navigation.Screens
import com.samkt.intellisoft.utils.OneTimeEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _oneTimeEvents = Channel<OneTimeEvents>()
    val oneTimeEvents = _oneTimeEvents.receiveAsFlow()


    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.OnConfirmPasswordChange -> {
                _signUpScreenState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword
                    )
                }
            }

            SignUpScreenEvent.OnConfirmPasswordVisibilityChange -> {
                _signUpScreenState.update {
                    it.copy(
                        confirmPasswordVisible = !signUpScreenState.value.confirmPasswordVisible
                    )
                }
            }

            is SignUpScreenEvent.OnEmailChange -> {
                _signUpScreenState.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is SignUpScreenEvent.OnFirstNameChange -> {
                _signUpScreenState.update {
                    it.copy(
                        firstName = event.firstName
                    )
                }
            }

            is SignUpScreenEvent.OnLastNameChange -> {
                _signUpScreenState.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }

            is SignUpScreenEvent.OnPasswordChange -> {
                _signUpScreenState.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            SignUpScreenEvent.OnPasswordVisibilityChange -> {
                _signUpScreenState.update {
                    it.copy(
                        passwordVisible = !signUpScreenState.value.passwordVisible
                    )
                }
            }

            SignUpScreenEvent.OnSignUpClick -> {
                signUpUser()
            }
        }
    }

    private fun signUpUser() {
        clearErrorState()
        signUpScreenState.value.apply {
            when {
                email.isEmpty() -> {
                    _signUpScreenState.update {
                        it.copy(
                            emailError = "Please provide an email"
                        )
                    }
                    return@apply
                }

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    _signUpScreenState.update {
                        it.copy(emailError = "Please enter a valid email address")
                    }
                    return@apply
                }

                firstName.isEmpty() -> {
                    _signUpScreenState.update {
                        it.copy(
                            firstNameError = "Please provide a first name"
                        )
                    }
                    return@apply
                }

                lastName.isEmpty() -> {
                    _signUpScreenState.update {
                        it.copy(
                            lastNameError = "Please provide a last name"
                        )
                    }
                    return@apply
                }

                password.isEmpty() -> {
                    _signUpScreenState.update {
                        it.copy(
                            passwordError = "Please provide a password"
                        )
                    }
                    return@apply
                }

                password.length < 8 -> {
                    _signUpScreenState.update {
                        it.copy(
                            passwordError = "Password must be at least 8 characters"
                        )
                    }
                    return@apply
                }

                confirmPassword.isEmpty() -> {
                    _signUpScreenState.update {
                        it.copy(
                            confirmPasswordError = "Please provide a confirm password"
                        )
                    }
                    return@apply
                }

                password != confirmPassword -> {
                    _signUpScreenState.update {
                        it.copy(
                            confirmPasswordError = "Passwords do not match"
                        )
                    }
                    return@apply
                }
            }
            viewModelScope.launch {
                val signUp = SignUp(
                    email = email,
                    firstname = firstName,
                    lastname = lastName,
                    password = password
                )
                _signUpScreenState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                when (val result = authRepository.signUp(signUp)) {
                    is Result.Error -> {
                        _signUpScreenState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _oneTimeEvents.send(OneTimeEvents.ShowMessage(result.message))
                    }

                    is Result.Success -> {
                        _signUpScreenState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _oneTimeEvents.send(OneTimeEvents.Navigate(Screens.Login.route))
                    }
                }
            }
        }
    }

    private fun clearErrorState() {
        _signUpScreenState.update {
            it.copy(
                emailError = null,
                firstNameError = null,
                lastNameError = null,
                passwordError = null,
                confirmPasswordError = null
            )
        }
    }
}


data class SignUpScreenState(
    val email: String = "",
    val emailError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface SignUpScreenEvent {
    data class OnEmailChange(val email: String) : SignUpScreenEvent
    data class OnFirstNameChange(val firstName: String) : SignUpScreenEvent
    data class OnLastNameChange(val lastName: String) : SignUpScreenEvent
    data class OnPasswordChange(val password: String) : SignUpScreenEvent
    data class OnConfirmPasswordChange(val confirmPassword: String) : SignUpScreenEvent
    data object OnPasswordVisibilityChange : SignUpScreenEvent
    data object OnConfirmPasswordVisibilityChange : SignUpScreenEvent
    data object OnSignUpClick : SignUpScreenEvent
}