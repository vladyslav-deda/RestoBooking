package com.project.data.model

import com.project.domain.model.FoodEstablishmentType
import java.time.LocalTime

data class FoodEstablishmentDto(
    val name: String = "",
    val city: String = "",
    val address: String = "",
    val phoneForBooking:String = "",
    val description: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val tags:List<String> = emptyList(),
    val selectedTimeFrom: LocalTime? = null,
    val selectedTimeTo: LocalTime? = null,
    val photoUrlList: List<String> = emptyList(),
    val ownerName: String = "",
)