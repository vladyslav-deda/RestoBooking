package com.project.presentation.ui.view

import com.project.presentation.ui.view.register_food_establishment.Tag
import java.text.SimpleDateFormat
import java.util.Locale

data class HomeSearchViewState(
    val city: String = "",
    val selectedDate: Long? = null,
    val selectedTimeFrom: Long? = null,
    val selectedTimeTo: Long? = null,
    val tags: List<Tag> = emptyList(),
    val peopleCount: Int = 0,
) {
    private val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun getFormattedDate(): String? = selectedDate?.let {
        dateFormat.format(it)
    }

    fun getFormattedTimeFrom(): String? = selectedTimeFrom?.let {
        timeFormat.format(it)
    }

    fun getFormattedTimeTo(): String? = selectedTimeTo?.let {
        timeFormat.format(it)
    }

    fun isSearchButtonEnabled() = city.isNotEmpty() && tags.any { it.isSelected }
}
