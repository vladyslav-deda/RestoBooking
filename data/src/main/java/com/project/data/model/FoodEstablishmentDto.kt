package com.project.data.model

import com.project.domain.model.FoodEstablishmentType

data class FoodEstablishmentDto(
    val name: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Bar,
    val address: String = "",
    val description: String = "",
    val twoSeaterTableValue: Int = 0,
    val fourSeaterTableValue: Int = 0,
    val sixSeaterTableValue: Int = 0,
    val photoUrlList: List<String> = emptyList(),
    val ownerName: String = ""
)