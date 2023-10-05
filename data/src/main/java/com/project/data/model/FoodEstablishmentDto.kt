package com.project.data.model

import com.project.domain.model.FoodEstablishmentType
import java.util.Date

data class FoodEstablishmentDto(
    val name: String = "",
    val city: String = "",
    val address: String = "",
    val phoneForBooking:String = "",
    val description: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val tags:List<String> = emptyList(),
    val selectedTimeFrom: Date? = null,
    val selectedTimeTo: Date? = null,
    val photoUrlList: List<String> = emptyList(),
    val ownerName: String = "",
)