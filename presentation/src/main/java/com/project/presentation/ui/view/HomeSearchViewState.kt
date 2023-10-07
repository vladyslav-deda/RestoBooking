package com.project.presentation.ui.view

import com.project.presentation.ui.view.register_food_establishment.Tag

data class HomeSearchViewState(
    val city: String = "",
    val tags: List<Tag> = emptyList(),
) {

    fun isSearchButtonEnabled() = city.isNotEmpty() && tags.any { it.isSelected }
}
