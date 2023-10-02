package com.project.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.view.HomeSearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodEstablishmentRepository: FoodEstablishmentRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    fun onCityChanged(city: String) {
        _uiState.update {
            it.copy(
                homeSearchViewState = it.homeSearchViewState.copy(
                    city = city
                )
            )
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update {
            it.copy(
                homeSearchViewState = it.homeSearchViewState.copy(
                    selectedDate = date,
                    selectedTime = null
                )
            )
        }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update {
            it.copy(
                homeSearchViewState = it.homeSearchViewState.copy(
                    selectedTime = time
                )
            )
        }
    }

    fun onNumberOfPersonsChanged(number: Int) {
        _uiState.update {
            it.copy(
                homeSearchViewState = it.homeSearchViewState.copy(
                    numberOfPersons = number
                )
            )
        }
    }

    fun onSearchClicked() {

    }
}

data class HomeUIState constructor(
    val list: List<FoodEstablishment> = emptyList(),
    val homeSearchViewState: HomeSearchViewState = HomeSearchViewState()
) {

}
