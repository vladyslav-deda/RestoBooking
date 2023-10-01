package com.project.presentation.ui.screens.add_food_establishments

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.model.FoodEstablishmentType
import com.project.domain.model.Photo
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.domain.usecase.GetCurrentUserUseCase
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep
import com.project.presentation.ui.view.register_food_establishment.AddPhotoViewState
import com.project.presentation.ui.view.register_food_establishment.MainInfoViewState
import com.project.presentation.ui.view.register_food_establishment.TablesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodEstablishmentsScreenViewModel @Inject constructor(
    private val foodEstablishmentRepository: FoodEstablishmentRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddFoodEstablishmentsUIState> =
        MutableStateFlow(AddFoodEstablishmentsUIState())
    val uiState: StateFlow<AddFoodEstablishmentsUIState> = _uiState.asStateFlow()


    fun onMainInfoNameChanged(name: String) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    name = name
                )
            )
        }
    }

    fun onMainInfoTypeChanged(foodEstablishmentType: FoodEstablishmentType) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    foodEstablishmentType = foodEstablishmentType
                )
            )
        }
    }

    fun onMainInfoAddressChanged(address: String) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    address = address
                )
            )
        }
    }

    fun onMainInfoDescriptionChanged(description: String) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    description = description
                )
            )
        }
    }

    fun onTwoSeaterTableValueIncreased() {
        val newValue = _uiState.value.tablesViewState.twoSeaterTableValue.plus(1)
        _uiState.update {
            it.copy(
                tablesViewState = it.tablesViewState.copy(
                    twoSeaterTableValue = newValue
                )
            )
        }
    }

    fun onTwoSeaterTableValueDecreased() {
        val newValue = _uiState.value.tablesViewState.twoSeaterTableValue.minus(1)
        if (newValue < 0) return
        _uiState.update {
            it.copy(
                tablesViewState = it.tablesViewState.copy(
                    twoSeaterTableValue = newValue
                )
            )
        }
    }

    fun onFourSeaterTableValueChangedIncreased() {
        val newValue = _uiState.value.tablesViewState.fourSeaterTableValue.plus(1)
        _uiState.update {
            it.copy(
                tablesViewState = it.tablesViewState.copy(
                    fourSeaterTableValue = newValue
                )
            )
        }
    }

    fun onFourSeaterTableValueChangedDecreased() {
        val newValue = _uiState.value.tablesViewState.fourSeaterTableValue.minus(1)
        if (newValue < 0) return
        _uiState.update {
            it.copy(
                tablesViewState = it.tablesViewState.copy(
                    fourSeaterTableValue = newValue
                )
            )
        }
    }

    fun onSixSeaterTableValueChangedIncreased() {
        val newValue = _uiState.value.tablesViewState.sixSeaterTableValue.plus(1)
        _uiState.update {
            it.copy(
                tablesViewState = it.tablesViewState.copy(
                    sixSeaterTableValue = newValue
                )
            )
        }
    }

    fun onSixSeaterTableValueChangedDecreased() {
        val newValue = _uiState.value.tablesViewState.sixSeaterTableValue.minus(1)
        if (newValue < 0) return
        _uiState.update {
            it.copy(
                tablesViewState = it.tablesViewState.copy(
                    sixSeaterTableValue = newValue
                )
            )
        }
    }


    fun onContinueClicked() {
        /**
         * The stepNumber of currentStep equal to index of next step
         */
        val nextStep =
            AddFoodEstablishmentStep.values()[_uiState.value.currentStep.stepNumber]
        _uiState.update {
            it.copy(
                currentStep = nextStep
            )
        }
    }

    fun decreaseStepNumber() {
        val nextStepNumber = _uiState.value.currentStep.stepNumber - 1
        AddFoodEstablishmentStep.values().firstOrNull {
            it.stepNumber == nextStepNumber
        }?.let {
            _uiState.update {
                it.copy(
                    currentStep = it.currentStep
                )
            }
        }
    }

    fun changePhoto(index: Int, uri: Uri) {
        val newList = _uiState.value.addPhotoViewState.photoList.toMutableList()
        newList[index] = Photo(index, uri)
        _uiState.update {
            it.copy(
                addPhotoViewState = AddPhotoViewState(photoList = newList)
            )
        }
    }

    fun onRegisterClicked() {
        viewModelScope.launch {
            foodEstablishmentRepository.registerFoodEstablishment(
                foodEstablishment = FoodEstablishment(
                    name = _uiState.value.mainInfoViewState.name ?: "",
                    foodEstablishmentType = _uiState.value.mainInfoViewState.foodEstablishmentType!!,
                    address = _uiState.value.mainInfoViewState.address ?: "",
                    description = _uiState.value.mainInfoViewState.description ?: "",
                    twoSeaterTableValue = _uiState.value.tablesViewState.twoSeaterTableValue,
                    fourSeaterTableValue = _uiState.value.tablesViewState.fourSeaterTableValue,
                    sixSeaterTableValue = _uiState.value.tablesViewState.sixSeaterTableValue,
                    photoList = _uiState.value.addPhotoViewState.photoList,
                    ownerName = getCurrentUserUseCase.invoke().getOrNull()?.nameSurname ?: ""
                )
            ).fold(
                onSuccess = {

                },
                onFailure = {

                }
            )
        }
    }
}

data class AddFoodEstablishmentsUIState(
    val currentStep: AddFoodEstablishmentStep = AddFoodEstablishmentStep.AddFoodEstablishmentMainInfo,
    val mainInfoViewState: MainInfoViewState = MainInfoViewState(),
    val tablesViewState: TablesViewState = TablesViewState(),
    val addPhotoViewState: AddPhotoViewState = AddPhotoViewState()
) {
    private fun getMaxSteps() = 3

    fun getProgress() =
        if (currentStep.stepNumber == getMaxSteps()) 1f else currentStep.stepNumber * 0.3f

    fun getTitle(): String {
        return "${currentStep.stepNumber}/${getMaxSteps()} ${currentStep.title}"
    }
}