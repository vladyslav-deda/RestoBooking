package com.project.presentation.ui.screens.myreservations.view

data class MyReservationItemViewState(
    val foodEstablishmentId: String,
    val name: String = "",
    val date: String = "",
    val address: String = ""
)
