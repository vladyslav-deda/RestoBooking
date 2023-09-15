package com.project.presentation.ui.screens.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.User
import com.project.domain.usecase.LoginUserUseCase
import com.project.domain.usecase.SignUpUserUseCase
import com.project.presentation.R
import com.project.presentation.ui.view.SignUpViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val application: Application,
    private val signUpUserUseCase: SignUpUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    fun onNameAndSurnameInputChanged(nameAndSurname: String) {
        _uiState.update {
            it.copy(
                signUpState = it.signUpState.copy(
                    nameAndSurname = nameAndSurname,
                    errorMessage = "",
                )
            )
        }
    }

    fun onEmailInputChanged(email: String) {
        _uiState.update {
            it.copy(
                signUpState = it.signUpState.copy(
                    email = email,
                    errorMessage = "",
                )
            )
        }
    }

    fun onPasswordInputChanged(password: String) {
        _uiState.update {
            it.copy(
                signUpState = it.signUpState.copy(
                    errorMessage = "",
                    password = it.signUpState.password.copy(password = password),
                )
            )
        }
    }

    fun onPasswordVisibilityChanged() {
        _uiState.update {
            it.copy(
                signUpState = it.signUpState.copy(
                    errorMessage = "",
                    password = it.signUpState.password.copy(showPassword = it.signUpState.password.showPassword.not()),
                )
            )
        }
    }

    fun onDuplicatePasswordVisibilityChanged() {
        _uiState.update {
            it.copy(
                signUpState = it.signUpState.copy(
                    errorMessage = "",
                    duplicatePassword = it.signUpState.duplicatePassword.copy(showPassword = it.signUpState.duplicatePassword.showPassword.not())
                )
            )
        }
    }

    fun onDuplicatePasswordInputChanged(password: String) {
        _uiState.update {
            it.copy(
                signUpState = it.signUpState.copy(
                    errorMessage = "",
                    duplicatePassword = it.signUpState.duplicatePassword.copy(password = password)
                )
            )
        }
    }

    fun onInputFieldFocusChanged(isFocused: Boolean) {
        _uiState.update {
            it.copy(
                signUpState = it.signUpState.copy(isInputFieldInFocus = isFocused)
            )
        }
    }

    fun onRegisterClick() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val nameSurname = _uiState.value.signUpState.nameAndSurname
            val email = _uiState.value.signUpState.email.trim()
            val password = _uiState.value.signUpState.password.password.trim()
            val duplicatePassword = _uiState.value.signUpState.duplicatePassword.password.trim()

            if (email.isEmpty() || password.isEmpty() || duplicatePassword.isEmpty()) {
                _uiState.update {
                    it.copy(
                        signUpState = it.signUpState.copy(errorMessage = application.getString(R.string.all_fields_must_be_filled)),
                        isLoading = false
                    )
                }
                return@launch
            }

            if (password != duplicatePassword) {
                _uiState.update {
                    it.copy(
                        signUpState = it.signUpState.copy(errorMessage = application.getString(R.string.different_passwords_message)),
                        isLoading = false
                    )
                }
                return@launch
            }
            val newUser = User(
                nameSurname = nameSurname,
                email = email,
                password = password
            )
            signUpUserUseCase.invoke(
                user = newUser
            )
            loginUserUseCase.invoke(
                email = email,
                password = password
            ).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isNavigateHome = true
                        )
                    }
                },
                onFailure = { throwable ->
                    _uiState.update {
                        it.copy(
                            signUpState = it.signUpState.copy(errorMessage = throwable.localizedMessage)
                        )
                    }
                }
            )
        }
    }
}

data class SignUpUIState(
    val signUpState: SignUpViewState = SignUpViewState(),
    val isLoading: Boolean = false,
    val isNavigateHome: Boolean = false
)