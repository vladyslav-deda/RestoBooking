package com.project.domain.model

data class ReservationModel(
    val reservatorName: String,
    val reservatorEmail: String,
    val peopleCount: Int,
    val reservationTimeFrom: Long,
    val reservationTimeTo: Long,
    val comment: String
)