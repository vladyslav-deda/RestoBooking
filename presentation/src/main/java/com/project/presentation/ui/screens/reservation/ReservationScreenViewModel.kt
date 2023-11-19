package com.project.presentation.ui.screens.reservation

import androidx.lifecycle.ViewModel
import com.project.domain.repository.FoodEstablishmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReservationScreenViewModel @Inject constructor(
    private val  foodEstablishmentRepository: FoodEstablishmentRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ReservationScreenUiState> =
        MutableStateFlow(ReservationScreenUiState())
    val uiState: StateFlow<ReservationScreenUiState> = _uiState.asStateFlow()

    fun onCommentChanged(comment: String) {
        _uiState.update {
            it.copy(
                comment = comment
            )
        }
    }
}

data class ReservationScreenUiState(
    val isLoading: Boolean = false,
    val comment: String? = null
)