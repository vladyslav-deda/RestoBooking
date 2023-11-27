package com.project.presentation.ui.screens.srp

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.navigation.ArgsName.CITY_ARG
import com.project.presentation.ui.navigation.ArgsName.TAGS_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SrpViewModel @Inject constructor(
    private val foodEstablishmentRepository: FoodEstablishmentRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val city: String = checkNotNull(savedStateHandle[CITY_ARG])
    private val tags: List<String> =
        (checkNotNull(savedStateHandle[TAGS_ARG]) as Array<String>).toList()

    private val _uiState: MutableStateFlow<SrpUIState> =
        MutableStateFlow(SrpUIState())
    val uiState: StateFlow<SrpUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            foodEstablishmentRepository.fetchFoodEstablishments(
                city = city.trim(),
                withTimeFilters = true
            ).fold(
                onSuccess = { list ->
                    _uiState.update {
                        it.copy(
                            list = list,
                            isLoading = false
                        )
                    }
                    Log.i("myLogs", "onSuccess: ${list.size}, city = $city")
                },
                onFailure = {
                    Timber.e("error: " + it.localizedMessage)
                }
            )
        }
    }
}

data class SrpUIState(
    val isLoading: Boolean = false,
    val navigateToSrp: Boolean = false,
    val list: List<FoodEstablishment> = emptyList()
)