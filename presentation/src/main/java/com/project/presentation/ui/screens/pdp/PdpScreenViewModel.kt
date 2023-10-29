package com.project.presentation.ui.screens.pdp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.navigation.PdpDestinationArgs.ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class PdpScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val foodEstablishmentRepository: FoodEstablishmentRepository,
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle[ID_ARG])

    private val _uiState: MutableStateFlow<PdpUIState> = MutableStateFlow(PdpUIState())
    val uiState: StateFlow<PdpUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            foodEstablishmentRepository.getFoodEstablishmentById(
                id = id.trim()
            ).fold(
                onSuccess = { foodEstablishment ->
                    _uiState.update {
                        it.copy(
                            foodEstablishment = foodEstablishment,
                            isLoading = false
                        )
                    }
                },
                onFailure = {
                    Timber.e("Error: " + it.localizedMessage)
                }
            )
            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    fun getWorkingHours(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val timeFrom = _uiState.value.foodEstablishment?.selectedTimeFrom
        val timeTo = _uiState.value.foodEstablishment?.selectedTimeTo
        return "${dateFormat.format(timeFrom)}-${dateFormat.format(timeTo)}"
    }

    fun showAddCommentDialog(value: Boolean) {
        _uiState.update {
            it.copy(
                showAddCommentDialog = value
            )
        }
    }

    fun addComment(commentText: String, rating: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    showAddCommentDialog = false
                )
            }
            foodEstablishmentRepository.addComment(
                foodEstablishmentId = id,
                commentText = commentText,
                rating = rating
            ).fold(
                onSuccess = {

                    Timber.e("Good")
                },
                onFailure = {
                    Timber.e(it)
                }
            )
            foodEstablishmentRepository.getFoodEstablishmentById(
                id = id.trim()
            ).fold(
                onSuccess = { foodEstablishment ->
                    _uiState.update {
                        it.copy(
                            foodEstablishment = foodEstablishment,
                            isLoading = false
                        )
                    }
                },
                onFailure = {
                    Timber.e("Error: " + it.localizedMessage)
                }
            )
            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }



}

data class PdpUIState(
    val isLoading: Boolean = false,
    val navigateToSrp: Boolean = false,
    val foodEstablishment: FoodEstablishment? = null,
    val showAddCommentDialog: Boolean = false
)