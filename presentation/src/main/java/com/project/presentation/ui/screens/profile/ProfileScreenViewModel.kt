package com.project.presentation.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.User
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.domain.repository.UserRepository
import com.project.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userRepository: UserRepository,
    private val foodEstablishmentRepository: FoodEstablishmentRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfileUIState> = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val currentUser = getCurrentUserUseCase.invoke().getOrNull()

            var unrepliedCommentsCount = 0
            foodEstablishmentRepository.getFoodEstablishmentOfCurrentUser()
                .getOrNull()?.let {
                    it.forEach {
                        it.comments.forEach {
                            if (it.textOfReplyToComment.isNullOrEmpty()){
                                unrepliedCommentsCount++
                            }
                        }
                    }
                }
            _uiState.update {
                ProfileUIState(
                    currentUser = currentUser,
                    unrepliedCommentsCount = unrepliedCommentsCount
                )
            }

        }
    }

    fun logout() {
        userRepository.logout()
    }
}

data class ProfileUIState(
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val unrepliedCommentsCount: Int? = null
)