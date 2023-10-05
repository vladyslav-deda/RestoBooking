package com.project.domain.model

import java.time.LocalTime

data class FoodEstablishment(
    val name: String = "",
    val city: String = "",
    val address: String = "",
    val phoneForBooking:String = "",
    val description: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val tags:List<String> = emptyList(),
    val selectedTimeFrom: LocalTime? = null,
    val selectedTimeTo: LocalTime? = null,
    val photoList: List<Photo> = emptyList(),
    val ownerName: String = ""
)
