package com.project.domain.model

data class Reservation(
    val timeSlot: TimeSlot? = null,
    val foodEstablishment: FoodEstablishment
)
