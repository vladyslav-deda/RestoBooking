package com.project.domain.model

data class FoodEstablishment(
    val id: String = "",
    val name: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val address: String = "",
    val description: String = "",
    val twoSeaterTableValue: Int = 0,
    val fourSeaterTableValue: Int = 0,
    val sixSeaterTableValue: Int = 0,
    val photoList: List<Photo> = emptyList(),
    val ownerName: String = "",
    val city: String = ""
)
