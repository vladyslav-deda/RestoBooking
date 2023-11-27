package com.project.presentation.ui.screens.myfoodestablishment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.screens.myfoodestablishment.view.MyFoodEstablishmentViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyFoodEstablishmentsViewModel @Inject constructor(
    private val foodEstablishmentRepository: FoodEstablishmentRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyFoodEstablishmentsUiModel> =
        MutableStateFlow(MyFoodEstablishmentsUiModel())
    val uiState: StateFlow<MyFoodEstablishmentsUiModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            foodEstablishmentRepository.getFoodEstablishmentOfCurrentUser()
                .fold(
                    onSuccess = { list ->
                        val resultList = list.map {
                            val name = "${it.foodEstablishmentType.title} ${it.name}"
                            val address = "${it.address}, ${it.city}"
                            MyFoodEstablishmentViewState(
                                id = it.id,
                                name = name,
                                rating = it.rating,
                                address = address
                            )
                        }
                        _uiState.update { it.copy(items = resultList) }
                    },
                    onFailure = {
                        Timber.e(it)
                    }
                )
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class MyFoodEstablishmentsUiModel(
    val isLoading: Boolean = false,
    val items: List<MyFoodEstablishmentViewState> = emptyList(),
)