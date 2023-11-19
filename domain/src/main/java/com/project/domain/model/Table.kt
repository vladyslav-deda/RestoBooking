package com.project.domain.model

data class Table(
    val timeSlots: List<TimeSlot> = emptyList()
)

data class TimeSlot(
    val timeFrom: Long = 0,
    val timeTo: Long = 0,
    val reservatorName: String? = null,
    val reservatorEmail: String? = null,
    val notes: String? = null
)