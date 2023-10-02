package com.project.presentation.ui.view

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class HomeSearchViewState(
    val city: String = "",
    val selectedDate: LocalDate? = null,
    val selectedTimeFrom: LocalTime? = null,
    val selectedTimeTo: LocalTime? = null,
    val numberOfPersons: Int = 0
) {

    private var dateFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun getFormattedDate() = selectedDate?.format(dateFormatter)

    fun getFormattedFromTime() = selectedTimeFrom?.format(timeFormatter)

    fun getFormattedToTime() = selectedTimeTo?.format(timeFormatter)

    fun getListOfPersonsNumber() = listOf(2, 4, 6)

    fun isSearchButtonEnabled() =
        city.isNotEmpty() && selectedDate != null && selectedTimeFrom != null && selectedTimeTo != null && numberOfPersons > 0
}
