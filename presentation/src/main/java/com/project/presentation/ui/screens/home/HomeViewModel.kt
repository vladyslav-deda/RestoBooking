package com.project.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.view.HomeSearchViewState
import com.project.presentation.ui.view.register_food_establishment.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodEstablishmentRepository: FoodEstablishmentRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            foodEstablishmentRepository.fetchFoodEstablishments()
                .fold(
                    onSuccess = {
                        val tagsTitleList = mutableListOf<String>()
                        it.forEach {
                            it.tags.forEach {
                                if (!tagsTitleList.contains(it)) {
                                    tagsTitleList.add(it)
                                }
                            }

                        }
                        val tags = tagsTitleList.map {
                            Tag(
                                title = it
                            )
                        }
                        _uiState.update {
                            it.copy(
                                homeSearchViewState = it.homeSearchViewState.copy(
                                    tags = tags
                                )
                            )
                        }
                    },
                    onFailure = {
                        Timber.e(it)
                    }
                )
            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    fun onCityChanged(city: String) {
        _uiState.update {
            it.copy(
                homeSearchViewState = it.homeSearchViewState.copy(
                    city = city
                )
            )
        }
    }

    fun handleTagSelection(tag: Tag) {
        val index = _uiState.value.homeSearchViewState.tags.indexOf(tag)
        val newTag = Tag(
            title = tag.title,
            isSelected = !tag.isSelected
        )
        val newSelectedList = _uiState.value.homeSearchViewState.tags.toMutableList()
        newSelectedList.removeAt(index)
        newSelectedList.add(index, newTag)
        _uiState.update {
            it.copy(
                homeSearchViewState = it.homeSearchViewState.copy(
                    tags = newSelectedList
                )
            )
        }
    }

    fun onSearchClicked() {
        _uiState.update {
            it.copy(
                continueClicked = true
            )
        }
    }

    fun resetContinueClickedStatus() {
        _uiState.update {
            it.copy(
                continueClicked = false
            )
        }
    }

    fun onDateChanged(value: Long) {
        _uiState.update {
            it.copy(
                homeSearchViewState = it.homeSearchViewState.copy(
                    selectedDate = value
                )
            )
        }
    }
}

data class HomeUIState(
    val isLoading: Boolean = false,
    val list: List<FoodEstablishment> = emptyList(),
    val homeSearchViewState: HomeSearchViewState = HomeSearchViewState(),
    val continueClicked: Boolean = false
)
