package com.project.presentation.ui.view

import com.project.domain.model.FoodEstablishmentType
import com.project.domain.model.Photo

data class SrpItemViewState(
    val photo: Photo = Photo(),
    val name: String = "",
    val foodEstablishmentType: FoodEstablishmentType = FoodEstablishmentType.Default,
    val rating: Int = 0,
    val tags: List<String> = emptyList()
)
