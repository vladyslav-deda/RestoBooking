package com.project.presentation.ui.screens.setreservation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.TimeSlot
import com.project.domain.repository.ReservationRepository
import com.project.domain.repository.SelectedDateForBookingLocalRepository
import com.project.presentation.ui.navigation.PdpDestinationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SetReservationScreenViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle[PdpDestinationArgs.ID_ARG])

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

    fun finishReservation() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            reservationRepository.addReservation(
                foodEstablishmentId = id,
                timeSlot = TimeSlot(
                    timeFrom = SelectedDateForBookingLocalRepository.getSelectedTimeFrom(),
                    timeTo = SelectedDateForBookingLocalRepository.getSelectedTimeTo(),
                    notes = _uiState.value.comment
                )
            ).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(navigateBack = true)
                    }
                },
                onFailure = {
                    Timber.e(it)
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            )
        }
    }
}

data class ReservationScreenUiState(
    val isLoading: Boolean = false,
    val comment: String? = null,
    val navigateBack: Boolean = false
)