package com.project.domain.repository

import java.util.Calendar

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

    fun getSelectedTimeFrom():Long {
        val calendarDate = Calendar.getInstance().apply {
            timeInMillis = date
        }
        val calendarTimeFrom = Calendar.getInstance().apply {
            timeInMillis = selectedTimeFrom
        }
        calendarDate.set(Calendar.HOUR_OF_DAY, calendarTimeFrom.get(Calendar.HOUR_OF_DAY))
        calendarDate.set(Calendar.MINUTE, calendarTimeFrom.get(Calendar.MINUTE))
        return calendarDate.timeInMillis
    }

    fun getSelectedTimeTo() :Long {
        val calendarDate = Calendar.getInstance().apply {
            timeInMillis = date
        }
        val calendarTimeTo = Calendar.getInstance().apply {
            timeInMillis = selectedTimeTo
        }
        calendarDate.set(Calendar.HOUR_OF_DAY, calendarTimeTo.get(Calendar.HOUR_OF_DAY))
        calendarDate.set(Calendar.MINUTE, calendarTimeTo.get(Calendar.MINUTE))
        return calendarDate.timeInMillis
    }

    fun getPeopleCount() = peopleCount
}