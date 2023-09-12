package com.project.presentation.ui.screens.signup

import androidx.lifecycle.ViewModel
import com.project.presentation.ui.view.SignUpViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    fun onEmailInputChanged(email: String) {
        _uiState.update {
            it.copy(
                signUpState = SignUpViewState(
                    email = email,
                    password = it.signUpState.password.copy(isError = false),
                    duplicatePassword = it.signUpState.duplicatePassword.copy(isError = false)
                )
            )
        }
    }

    fun onPasswordInputChanged(password: String) {
        _uiState.update {
            val passwordViewState =
                it.signUpState.password.copy(password = password, isError = false)
            it.copy(
                signUpState = SignUpViewState(
                    email = it.signUpState.email,
                    password = passwordViewState,
                    duplicatePassword = it.signUpState.duplicatePassword.copy(isError = false)
                )
            )
        }
    }

    fun onPasswordVisibilityChanged() {
        _uiState.update {
            val passwordViewState =
                it.signUpState.password.copy(showPassword = it.signUpState.password.showPassword.not())
            it.copy(
                signUpState = SignUpViewState(
                    email = it.signUpState.email,
                    password =passwordViewState,
                    duplicatePassword = it.signUpState.duplicatePassword.copy(isError = false)
                )
            )
        }
    }

    fun onDuplicatePasswordVisibilityChanged() {
        _uiState.update {
            val duplicateViewState =
                it.signUpState.duplicatePassword.copy(showPassword = it.signUpState.duplicatePassword.showPassword.not())
            it.copy(
                signUpState = SignUpViewState(
                    email = it.signUpState.email,
                    password = it.signUpState.password.copy(isError = false),
                    duplicatePassword = duplicateViewState
                )
            )
        }
    }

    fun onDuplicatePasswordInputChanged(password: String) {
        _uiState.update {
            val duplicateViewState =
                it.signUpState.duplicatePassword.copy(password = password, isError = false)

            it.copy(
                signUpState = SignUpViewState(
                    email = it.signUpState.email,
                    password = it.signUpState.password.copy(isError = false),
                    duplicatePassword = duplicateViewState
                )
            )
        }
    }
}

data class SignUpUIState(
    val signUpState : SignUpViewState = SignUpViewState(),
    val isLoading: Boolean = false,
    val isNavigateHome: Boolean = false
)