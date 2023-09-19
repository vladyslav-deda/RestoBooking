package com.project.presentation.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.project.domain.model.User
import com.project.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfileUIState> = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    init {
        val currentUser = getCurrentUserUseCase.invoke().getOrNull()
        _uiState.update {
            ProfileUIState(
                currentUser = currentUser
            )
        }
    }
}

data class ProfileUIState(
    val currentUser: User? = null
)