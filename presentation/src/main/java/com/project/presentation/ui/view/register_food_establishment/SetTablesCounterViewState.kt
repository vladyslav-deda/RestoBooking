package com.project.presentation.ui.view.register_food_establishment

data class SetTablesCounterViewState(
    var tablesCount: Int = 0
) {
    fun isContinueButtonEnabled() = tablesCount > 0
}
