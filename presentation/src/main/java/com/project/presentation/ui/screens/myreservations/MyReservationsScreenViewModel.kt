package com.project.presentation.ui.screens.myreservations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.model.Reservation
import com.project.domain.model.TimeSlot
import com.project.domain.repository.ReservationRepository
import com.project.presentation.ui.screens.myreservations.view.MyReservationItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyReservationsScreenViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyReservationsScreenUiState> =
        MutableStateFlow(MyReservationsScreenUiState())
    val uiState: StateFlow<MyReservationsScreenUiState> = _uiState.asStateFlow()

    private val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    init {
        retrieveReservations()
    }

    fun removeReservation(
        foodEstablishmentId: String,
        timeSlot: TimeSlot
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            reservationRepository.removeReservation(foodEstablishmentId, timeSlot)
                .fold(
                    onSuccess = {
                        retrieveReservations()
                    },
                    onFailure = {
                        Timber.e(it)
                    }
                )
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun retrieveReservations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            reservationRepository.getReservationsForCurrentUser()
                .fold(
                    onSuccess = { reservation ->
                        val states: List<MyReservationItemViewState> = reservation.map {
                            val name =
                                "${it.foodEstablishment.foodEstablishmentType.title} ${it.foodEstablishment.name}"
                            val date = dateFormat.format(it.timeSlot?.timeFrom)
                            val timeFrom = timeFormat.format(it.timeSlot?.timeFrom)
                            val timeTo = timeFormat.format(it.timeSlot?.timeTo)
                            val reservationTime = "$date $timeFrom-$timeTo"
                            val address =
                                "${it.foodEstablishment.address}, ${it.foodEstablishment.city}"
                            MyReservationItemViewState(
                                foodEstablishmentId = it.foodEstablishment.id,
                                name = name,
                                date = reservationTime,
                                address = address,
                                timeSlot = it.timeSlot!!
                            )
                        }
                        _uiState.update {
                            it.copy(
                                reservations = states
                            )
                        }
                    },
                    onFailure = {
                        Timber.e(it)
                    }
                )
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class MyReservationsScreenUiState(
    val isLoading: Boolean = false,
    val reservations: List<MyReservationItemViewState> = emptyList()
)