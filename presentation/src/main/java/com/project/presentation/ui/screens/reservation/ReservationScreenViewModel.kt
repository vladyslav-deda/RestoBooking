package com.project.presentation.ui.screens.reservation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.project.presentation.ui.navigation.ReservationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReservationScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedDate: String =
        checkNotNull(savedStateHandle[ReservationArgs.SELECTED_DATE_ARG])

    private val _uiState: MutableStateFlow<ReservationScreenUiState> =
        MutableStateFlow(ReservationScreenUiState())
    val uiState: StateFlow<ReservationScreenUiState> = _uiState.asStateFlow()

    fun onStartTimeChanged(time: Long) {
        _uiState.update {
            it.copy(
                selectedTimeFrom = time
            )
        }
    }

    fun onEndTimeChanged(time: Long) {
        _uiState.update {
            it.copy(
                selectedTimeTo = time
            )
        }
    }

    fun onPeopleCounterChanged(value: Int) {
        _uiState.update {
            it.copy(
                peopleCount = value
            )
        }
    }

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
    val selectedTimeFrom: Long? = null,
    val selectedTimeTo: Long? = null,
    val peopleCount: Int = 0,
    val comment: String? = null
) {

    private var simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun getFormattedTimeFrom(): String? =
        if (selectedTimeFrom != null) simpleDateFormat.format(selectedTimeFrom) else null

    fun getFormattedTimeTo(): String? =
        if (selectedTimeTo != null) simpleDateFormat.format(selectedTimeTo) else null
}