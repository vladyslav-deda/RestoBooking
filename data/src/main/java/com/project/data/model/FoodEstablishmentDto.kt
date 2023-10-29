package com.project.data.model

import com.project.domain.model.Comment
import com.project.domain.model.FoodEstablishmentType

data class FoodEstablishmentDto(
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
    val photoUrlList: List<String> = emptyList(),
    val ownerName: String = "",
    val comments: List<Comment> = emptyList()
)