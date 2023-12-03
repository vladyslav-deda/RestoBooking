package com.project.presentation.ui.screens.statistic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.domain.repository.SurveyData
import com.project.presentation.ui.navigation.ArgsName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenViewModel @Inject constructor(
    foodEstablishmentRepository: FoodEstablishmentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle[ArgsName.ID_ARG])

    private val _uiState: MutableStateFlow<StatisticsScreenUiModel> =
        MutableStateFlow(StatisticsScreenUiModel())
    val uiState: StateFlow<StatisticsScreenUiModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            foodEstablishmentRepository.getFoodEstablishmentById(id).fold(
                onSuccess = {
                    var firstQuestionYes = 0
                    var firstQuestionNo = 0
                    var secondQuestionYes = 0
                    var secondQuestionNo = 0
                    var cuisineCounter = 0
                    var serviceCounter = 0
                    var atmosphereCounter = 0
                    var locationCounter = 0
                    var nothing = 0
                    it.statisticModelList.forEach { statistic ->
                        statistic.map?.forEach { (taskIndex, answer) ->
                            val taskIndexInt = taskIndex.toInt()
                            if (taskIndexInt == 0) {
                                if (answer == SurveyData.getData()[0].answers[0]) {
                                    firstQuestionYes++
                                } else {
                                    firstQuestionNo++
                                }
                            } else if (taskIndexInt == 1) {
                                if (answer == SurveyData.getData()[1].answers[0]) {
                                    secondQuestionYes++
                                } else {
                                    secondQuestionNo++
                                }
                            } else if (taskIndexInt == 2) {
                                when (answer) {
                                    SurveyData.getData()[2].answers[0] -> cuisineCounter++
                                    SurveyData.getData()[2].answers[1] -> serviceCounter++
                                    SurveyData.getData()[2].answers[2] -> atmosphereCounter++
                                    SurveyData.getData()[2].answers[3] -> locationCounter++
                                    else -> nothing++
                                }
                            }
                        }
                    }
                    _uiState.update {
                        it.copy(
                            firstQuestionPair = Pair(firstQuestionYes, firstQuestionNo),
                            secondQuestionPair = Pair(secondQuestionYes, secondQuestionNo),
                            thirdList = listOf(
                                cuisineCounter.toFloat(),
                                serviceCounter.toFloat(),
                                atmosphereCounter.toFloat(),
                                locationCounter.toFloat(),
                                nothing.toFloat()
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
                    isLoading = false,
                )
            }
        }
    }
}

data class StatisticsScreenUiModel(
    val isLoading: Boolean = false,
    val firstQuestionPair: Pair<Int, Int>? = null,
    val secondQuestionPair: Pair<Int, Int>? = null,
    val thirdList: List<Float> = emptyList()
)