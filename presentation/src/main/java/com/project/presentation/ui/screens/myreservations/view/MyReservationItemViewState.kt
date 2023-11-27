package com.project.presentation.ui.screens.myreservations.view

import com.project.domain.model.TimeSlot

data class MyReservationItemViewState(
    val foodEstablishmentId: String,
    val name: String = "",
    val date: String = "",
    val address: String = "",
    val timeSlot: TimeSlot = TimeSlot()
)
