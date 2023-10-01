package com.project.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodEstablishmentRepository: FoodEstablishmentRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            foodEstablishmentRepository.fetchFoodEstablishments()
                .fold(
                    onSuccess = { list ->
                        _uiState.update {
                            it.copy(
                                list = list
                            )
                        }
                        val test = _uiState.value.list
                        val t = 0
                    },
                    onFailure = {

                    }
                )
        }
    }
}

data class HomeUIState(
    val list: List<FoodEstablishment> = emptyList()
)
