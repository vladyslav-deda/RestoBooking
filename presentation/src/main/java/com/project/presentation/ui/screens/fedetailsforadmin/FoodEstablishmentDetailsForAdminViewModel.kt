package com.project.presentation.ui.screens.fedetailsforadmin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.navigation.ArgsName.ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FoodEstablishmentDetailsForAdminViewModel @Inject constructor(
    foodEstablishmentRepository: FoodEstablishmentRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle[ID_ARG])

    private val _uiState: MutableStateFlow<FoodEstablishmentDetailsForAdminUiState> =
        MutableStateFlow(FoodEstablishmentDetailsForAdminUiState())
    val uiState: StateFlow<FoodEstablishmentDetailsForAdminUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            foodEstablishmentRepository.getFoodEstablishmentById(id)
                .fold(
                    onSuccess = { result ->
                        _uiState.update {
                            it.copy(foodEstablishment = result)
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

data class FoodEstablishmentDetailsForAdminUiState(
    val isLoading: Boolean = false,
    val foodEstablishment: FoodEstablishment? = null
)