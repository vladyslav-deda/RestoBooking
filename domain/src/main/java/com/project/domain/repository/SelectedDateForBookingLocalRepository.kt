package com.project.domain.repository

object SelectedDateForBookingLocalRepository {

    private var date: Long = 0
    private var selectedTimeFrom: Long = 0
    private var selectedTimeTo: Long = 0
    private var peopleCount: Int = 0

    fun saveData(
        date: Long,
        selectedTimeFrom: Long,
        selectedTimeTo: Long,
        peopleCount: Int,
    ) {
        this.date = date
        this.selectedTimeFrom = selectedTimeFrom
        this.selectedTimeTo = selectedTimeTo
        this.peopleCount = peopleCount
    }

    fun getSavedDate() = date

    fun getSelectedTimeFrom() = selectedTimeFrom

    fun getSelectedTimeTo() = selectedTimeTo

    fun getPeopleCount() = peopleCount
}