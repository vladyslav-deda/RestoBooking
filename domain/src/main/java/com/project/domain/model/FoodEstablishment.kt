package com.project.domain.model

import java.util.Date

data class FoodEstablishment(
    val id: String = "",
    val name: String = "",
    val city: String = "",
    val address: String = "",
    val phoneForBooking: String = "",
    val description: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val tags: List<String> = emptyList(),
    val selectedTimeFrom: Date? = null,
    val selectedTimeTo: Date? = null,
    val photoList: List<Photo> = emptyList(),
    val ownerName: String = "",
    val rating: Int = 0,
    val comments: List<String> = emptyList()
)
