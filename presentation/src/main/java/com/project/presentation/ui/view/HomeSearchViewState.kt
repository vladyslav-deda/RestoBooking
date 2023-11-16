package com.project.presentation.ui.view

import com.project.presentation.ui.view.register_food_establishment.Tag
import java.text.SimpleDateFormat
import java.util.Locale

data class HomeSearchViewState(
    val city: String = "",
    val selectedDate: Long? = null,
    val tags: List<Tag> = emptyList(),
) {
    private val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())

    fun getFormattedDate(): String? = selectedDate?.let {
        dateFormat.format(selectedDate)
    }

    fun isSearchButtonEnabled() = city.isNotEmpty() && tags.any { it.isSelected }
}
