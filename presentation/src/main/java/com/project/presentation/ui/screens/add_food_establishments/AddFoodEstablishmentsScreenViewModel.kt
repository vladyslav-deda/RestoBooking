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
import com.project.presentation.ui.screens.add_food_establishments.model.Tags
import com.project.presentation.ui.view.register_food_establishment.AddPhotoViewState
import com.project.presentation.ui.view.register_food_establishment.MainInfoViewState
import com.project.presentation.ui.view.register_food_establishment.AddTagsViewState
import com.project.presentation.ui.view.register_food_establishment.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
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

    fun onMainInfoCityChanged(city: String) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    city = city
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

    fun onMainInfoTimeFromSelected(time: Date) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    selectedTimeFrom = time,
                    selectedTimeTo = null
                )
            )
        }
    }

    fun onMainInfoTimeToSelected(time: Date) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    selectedTimeTo = time
                )
            )
        }
    }

    fun onMainInfoPhoneForReservationChanged(phone: String) {
        _uiState.update {
            it.copy(
                mainInfoViewState = it.mainInfoViewState.copy(
                    phoneForReservation = phone
                )
            )
        }
    }

    fun addTagToSelected(tag: Tag) {
        val newTag = Tag(
            title = tag.title,
            isSelected = true
        )
        val newSelectedList = _uiState.value.addTagsViewState.selectedTagsList.toMutableList()
        newSelectedList.add(newTag)
        val newRecommendedList = _uiState.value.addTagsViewState.recommendedTagsList.toMutableList()
        newRecommendedList.remove(tag)
        _uiState.update {
            it.copy(
                addTagsViewState = AddTagsViewState(
                    selectedTagsList = newSelectedList,
                    recommendedTagsList = newRecommendedList
                )
            )
        }
    }

    fun removeTagFromSelected(tag: Tag) {
        val newTag = Tag(
            title = tag.title,
            isSelected = false
        )
        val newSelectedList = _uiState.value.addTagsViewState.selectedTagsList.toMutableList()
        newSelectedList.remove(tag)
        val newRecommendedList = _uiState.value.addTagsViewState.recommendedTagsList.toMutableList()
        val isTagWasInitiallyRecommended = Tags.values().map { it.title }.contains(newTag.title)

        if (isTagWasInitiallyRecommended) {
            newRecommendedList.add(newTag)
        }
        _uiState.update {
            it.copy(
                addTagsViewState = AddTagsViewState(
                    selectedTagsList = newSelectedList,
                    recommendedTagsList = newRecommendedList
                )
            )
        }
    }

    fun setValueForAddNewTagClicked(value: Boolean) {
        _uiState.update {
            it.copy(
                addTagsViewState = it.addTagsViewState.copy(
                    addNewTagClicked = value
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
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val idOfNewFoodEstablishment = UUID.randomUUID().toString()
            foodEstablishmentRepository.registerFoodEstablishment(
                foodEstablishment = FoodEstablishment(
                    id = idOfNewFoodEstablishment,
                    name = _uiState.value.mainInfoViewState.name ?: "",
                    foodEstablishmentType = _uiState.value.mainInfoViewState.foodEstablishmentType!!,
                    address = _uiState.value.mainInfoViewState.address ?: "",
                    description = _uiState.value.mainInfoViewState.description ?: "",
                    photoList = _uiState.value.addPhotoViewState.photoList,
                    ownerName = getCurrentUserUseCase.invoke().getOrNull()?.nameSurname ?: "",
                    city = _uiState.value.mainInfoViewState.city ?: "",
                    selectedTimeTo = _uiState.value.mainInfoViewState.selectedTimeTo,
                    selectedTimeFrom = _uiState.value.mainInfoViewState.selectedTimeFrom,
                    phoneForBooking = _uiState.value.mainInfoViewState.phoneForReservation ?: "",
                    tags = _uiState.value.addTagsViewState.selectedTagsList.map { it.title }
                )
            ).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            navigateToProfile = true
                        )
                    }
                },
                onFailure = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            )
        }
    }
}

data class AddFoodEstablishmentsUIState(
    val isLoading: Boolean = false,
    val currentStep: AddFoodEstablishmentStep = AddFoodEstablishmentStep.AddFoodEstablishmentMainInfo,
    val mainInfoViewState: MainInfoViewState = MainInfoViewState(),
    val addTagsViewState: AddTagsViewState = AddTagsViewState(),
    val addPhotoViewState: AddPhotoViewState = AddPhotoViewState(),
    val navigateToProfile: Boolean = false,
    val isError: Boolean = false
) {
    private fun getMaxSteps() = 3

    fun getProgress() =
        if (currentStep.stepNumber == getMaxSteps()) 1f else currentStep.stepNumber * 0.3f

    fun getTitle(): String {
        return "${currentStep.stepNumber}/${getMaxSteps()} ${currentStep.title}"
    }
}