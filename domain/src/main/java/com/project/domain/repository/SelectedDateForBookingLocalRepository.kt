package com.project.domain.repository

object SelectedDateForBookingLocalRepository {

    private var date: Long = 0
    private var workingTimeFrom: Long = 0
    private var workingTimeTo: Long = 0

    fun saveDate(date: Long) {
        this.date = date
    }

    fun getSavedDate() = date

    fun saveWorkingTimeFrom(workingTimeFrom: Long) {
        this.workingTimeFrom = workingTimeFrom
    }

    fun getWorkingTimeFrom() = workingTimeFrom

    fun saveWorkingTimeTo(workingTimeTo: Long) {
        this.workingTimeTo = workingTimeTo
    }

    fun getWorkingTimeTo() = workingTimeTo


}