package com.project.domain.model

data class Table(
    val timeSlots: List<TimeSlot>
)

data class TimeSlot(
    val timeFrom: Long,
    val timeTo: Long,
    val reservatorName: String? = null,
    val reservatorEmail: String? = null,
    val notes: String? = null
)