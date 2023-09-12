package com.project.presentation.ui.screens.login

import androidx.lifecycle.ViewModel
import com.project.presentation.ui.view.EmailPasswordViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun onEmailInputChanged(email: String) {
        _uiState.update {
            it.copy(
                emailPassword = EmailPasswordViewState(
                    email,
                    it.emailPassword.password.copy(isError = false)
                )
            )
        }
    }

    fun onPasswordInputChanged(password: String) {
        _uiState.update {
            it.copy(
                emailPassword = EmailPasswordViewState(
                    email = it.emailPassword.email,
                    it.emailPassword.password.copy(password = password, isError = false)
                )
            )
        }
    }

    fun onPasswordVisibilityChanged() {
        _uiState.update {
            it.copy(
                emailPassword = EmailPasswordViewState(
                    email = it.emailPassword.email,
                    it.emailPassword.password.copy(showPassword = it.emailPassword.password.showPassword.not())
                )
            )
        }
    }

//    fun onSubmitClick() {
//        _uiState.update {
//            it.copy(isLoading = true)
//        }
//
//        viewModelScope.launch(Dispatchers.IO) {
//            val email = getEmail().replace(" ", "")
//            val password = getPassword()
//
//            if (email.isEmpty() || password.isEmpty()) {
//                processEmailPasswordErrorState(error = application.getString(R.string.not_valid_credentials_message))
//                return@launch
//            }
//            processLogin(loginUseCase.login(email, password))
//        }
//    }

    fun onSignUpClick() {
        _uiState.update {
            it.copy(isNavigateSignUp = true)
        }
    }

    fun resetSignUpStatus() {
        _uiState.update {
            it.copy(isNavigateSignUp = false)
        }
    }

//    private fun processEmailPasswordErrorState(error: String) {
//        _uiState.update {
//            val passwordState = it.emailPassword.password.copy(isError = true)
//            val filedUiState =
//                it.emailPassword.copy(errorMessage = error, password = passwordState)
//            it.copy(emailPassword = filedUiState, isLoading = false)
//        }
//    }

//    private fun processLogin(loginResult: Result<Unit>) {
//        loginResult.fold(onSuccess = {
//            _uiState.update { loginState ->
//                loginState.copy(isLoading = false, isNavigateHome = true)
//            }
//        }, onFailure = {
//            processEmailPasswordErrorState(
//                error = it.localizedMessage ?: application.getString(R.string.failed_authentication)
//            )
//        })
//    }

//    private fun getEmail(): String = _uiState.value.emailPassword.email
//    private fun getPassword(): String = _uiState.value.emailPassword.password.password
}

data class LoginUIState(
    val emailPassword: EmailPasswordViewState = EmailPasswordViewState(),
    val isLoading: Boolean = false,
    val isNavigateHome: Boolean = false,
    val isNavigateSignUp: Boolean = false
)
