package com.project.presentation.ui.screens.spalsh

import androidx.lifecycle.ViewModel
import com.project.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SplashScreenUIState> =
        MutableStateFlow(SplashScreenUIState())
    val uiState: StateFlow<SplashScreenUIState> = _uiState.asStateFlow()

    init {
        if (userRepository.isUserLoggedIn()) {
            _uiState.update {
                it.copy(
                    navigateHomeScreen = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    navigateLoginScreen = true
                )
            }
        }
    }
}

data class SplashScreenUIState(
    val navigateHomeScreen: Boolean = false,
    val navigateLoginScreen: Boolean = false
)