package com.project.domain.model

data class FoodEstablishment(
    val id: String = "",
    val name: String = "",
    val city: String = "",
    val address: String = "",
    val phoneForBooking: String = "",
    val description: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val tags: List<String> = emptyList(),
    val selectedTimeFrom: Long? = null,
    val selectedTimeTo: Long? = null,
    val photoList: List<Photo> = emptyList(),
    val ownerName: String = "",
    val rating: Float = 3.5f,
    val comments: List<Comment> = emptyList()
)
