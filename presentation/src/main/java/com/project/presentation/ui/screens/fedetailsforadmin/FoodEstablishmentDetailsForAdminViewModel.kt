package com.project.presentation.ui.screens.fedetailsforadmin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.model.TimeSlot
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.navigation.ArgsName.FE_DETAILS_FOR_ADMIN_ID_ARG
import com.project.presentation.ui.screens.fedetailsforadmin.view.TimeSlotDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
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
class FoodEstablishmentDetailsForAdminViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val foodEstablishmentRepository: FoodEstablishmentRepository,
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle[FE_DETAILS_FOR_ADMIN_ID_ARG])

    private val _uiState: MutableStateFlow<FoodEstablishmentDetailsForAdminUiState> =
        MutableStateFlow(FoodEstablishmentDetailsForAdminUiState())
    val uiState: StateFlow<FoodEstablishmentDetailsForAdminUiState> = _uiState.asStateFlow()

    private var foodEstablishment: FoodEstablishment? = null

    private val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())


    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            foodEstablishmentRepository.getFoodEstablishmentById(id)
                .fold(
                    onSuccess = { result ->
                        _uiState.update {
                            it.copy(foodEstablishment = result)
                        }
                        foodEstablishment = result
                    },
                    onFailure = {
                        Timber.e(it)
                    }
                )
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getReservedTimeSlots(): List<TimeSlotDetailsViewState> {
        val list = mutableListOf<TimeSlot>()
        foodEstablishment?.tablesForBooking?.forEach {
            it.timeSlots.forEach { timeSlot ->
                if (timeSlot.reservatorEmail?.isNotEmpty() == true) {
                    list.add(timeSlot)
                }
            }
        }
        val resultList = list.map { mainTimeSlot ->
            val date = dateFormat.format(mainTimeSlot.timeFrom)
            val timeSlot =
                "${timeFormat.format(mainTimeSlot.timeFrom)}-${timeFormat.format(mainTimeSlot.timeTo)}"
            var counterOfTables = 0
            foodEstablishment?.tablesForBooking?.forEach {
                it.timeSlots.forEach {
                    if (it.reservatorName?.isNotEmpty() == true
                        && it.reservatorName == mainTimeSlot.reservatorName
                        && it.timeFrom == mainTimeSlot.timeFrom
                    ) {
                        counterOfTables++
                    }
                }
            }
            TimeSlotDetailsViewState(
                date = date,
                timeSlot = timeSlot,
                reservatorName = mainTimeSlot.reservatorName,
                tablesCounter = counterOfTables,
                notes = mainTimeSlot.notes
            )
        }
        return resultList
    }

    fun handleTimeSlotsVisibility(value: Boolean) {
        _uiState.update { it.copy(isTimeSlotsShowed = value) }
    }
}

data class FoodEstablishmentDetailsForAdminUiState(
    val isLoading: Boolean = false,
    val foodEstablishment: FoodEstablishment? = null,
    val isTimeSlotsShowed: Boolean = false
)