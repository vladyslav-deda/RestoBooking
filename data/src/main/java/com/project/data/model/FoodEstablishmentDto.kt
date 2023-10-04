package com.project.data.model

import com.project.domain.model.FoodEstablishmentType
import java.time.LocalTime

data class FoodEstablishmentDto(
    val name: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val address: String = "",
    val description: String = "",
    val twoSeaterTableValue: Int = 0,
    val fourSeaterTableValue: Int = 0,
    val sixSeaterTableValue: Int = 0,
    val photoUrlList: List<String> = emptyList(),
    val ownerName: String = "",
    val city: String = "",
    val selectedTimeFrom: LocalTime? = null,
    val selectedTimeTo: LocalTime? = null,
)