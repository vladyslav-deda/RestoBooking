package com.project.presentation.ui.screens.bottom_navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.presentation.ui.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel @Inject constructor(
    private val foodEstablishmentRepository: FoodEstablishmentRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<BottomNavigationUiState> =
        MutableStateFlow(BottomNavigationUiState())
    val uiState: StateFlow<BottomNavigationUiState> = _uiState.asStateFlow()

    init {
        updateBadgeCounter()
    }

    fun updateBadgeCounter() {
        viewModelScope.launch {
            var unrepliedCommentsCount = 0
            foodEstablishmentRepository.getFoodEstablishmentOfCurrentUser()
                .getOrNull()?.let {
                    it.forEach {
                        it.comments.forEach {
                            if (it.textOfReplyToComment.isNullOrEmpty()) {
                                unrepliedCommentsCount++
                            }
                        }
                    }
                }
            Log.i("myLogs", "updateBadgeCounter: $unrepliedCommentsCount")
            _uiState.update { it.copy(unrepliedComments = unrepliedCommentsCount) }
        }
    }

    fun getCurrentScreen(route: String?): AppDestinations? = when (route) {
        AppDestinations.Home.route -> AppDestinations.Home
        AppDestinations.Reservations.route -> AppDestinations.Reservations
        AppDestinations.Profile.route -> AppDestinations.Profile
        else -> null
    }

    fun getListOfItems() =
        setOf(AppDestinations.Home, AppDestinations.Reservations, AppDestinations.Profile)

}

data class BottomNavigationUiState(
    val unrepliedComments: Int? = null
)