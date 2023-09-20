package com.project.presentation.ui.screens.add_food_establishments

import androidx.lifecycle.ViewModel
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep
import com.project.presentation.ui.screens.add_food_establishments.model.FoodEstablishmentType
import com.project.presentation.ui.view.register_food_establishment.MainInfoViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddFoodEstablishmentsScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<AddFoodEstablishmentsUIState> =
        MutableStateFlow(AddFoodEstablishmentsUIState())
    val uiState: StateFlow<AddFoodEstablishmentsUIState> = _uiState.asStateFlow()

    fun getTitle(): String {
        return "${_uiState.value.currentStep.stepNumber}/${_uiState.value.getMaxSteps()} ${_uiState.value.currentStep.title}"
    }

    fun onMainInfoNameChanged(name: String) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    name = name
                )
            )
        }
    }

    fun onMainInfoFoodEstablishmentTypeChanged(foodEstablishmentType: FoodEstablishmentType) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    foodEstablishmentType = foodEstablishmentType
                )
            )
        }
    }

}

data class AddFoodEstablishmentsUIState(
    val currentStep: AddFoodEstablishmentStep = AddFoodEstablishmentStep.MainInfo,
    val mainInfoViewState: MainInfoViewState = MainInfoViewState()
) {
    fun getMaxSteps() = 3

    fun getProgress() =
        if (currentStep.stepNumber == getMaxSteps()) 1f else currentStep.stepNumber * 0.3f
}