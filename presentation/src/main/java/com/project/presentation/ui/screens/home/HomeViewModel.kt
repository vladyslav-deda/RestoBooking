package com.project.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.view.HomeSearchViewState
import com.project.presentation.ui.view.register_food_establishment.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                homeSearchViewState = HomeSearchViewState(
                    tags = newSelectedList
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
)
