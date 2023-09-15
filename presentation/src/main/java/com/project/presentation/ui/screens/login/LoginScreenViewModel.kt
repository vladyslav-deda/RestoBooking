package com.project.presentation.ui.screens.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.usecase.LoginUserUseCase
import com.project.presentation.R
import com.project.presentation.ui.view.EmailPasswordViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val application: Application,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun onEmailInputChanged(email: String) {
        _uiState.update {
            it.copy(
                emailPassword = it.emailPassword.copy(
                    email = email,
                    errorMessage = ""
                )
            )
        }
    }

    fun onPasswordInputChanged(password: String) {
        _uiState.update {
            it.copy(
                emailPassword = it.emailPassword.copy(
                    password = it.emailPassword.password.copy(password = password),
                    errorMessage = ""
                )
            )
        }
    }

    fun onPasswordVisibilityChanged() {
        _uiState.update {
            it.copy(
                emailPassword = it.emailPassword.copy(
                    password = it.emailPassword.password.copy(showPassword = it.emailPassword.password.showPassword.not())
                )
            )
        }
    }

    fun onSignInClicked() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val email = _uiState.value.emailPassword.email.trim()
            val password = _uiState.value.emailPassword.password.password.trim()

            if (email.isEmpty() || password.isEmpty()) {
                _uiState.update {
                    it.copy(
                        emailPassword = it.emailPassword.copy(errorMessage = application.getString(R.string.all_fields_must_be_filled)),
                        isLoading = false
                    )
                }
                return@launch
            }
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
                            emailPassword = it.emailPassword.copy(errorMessage = throwable.localizedMessage),
                            isLoading = false
                        )
                    }
                }
            )
        }
    }
}

data class LoginUIState(
    val emailPassword: EmailPasswordViewState = EmailPasswordViewState(),
    val isLoading: Boolean = false,
    val isNavigateHome: Boolean = false
)
