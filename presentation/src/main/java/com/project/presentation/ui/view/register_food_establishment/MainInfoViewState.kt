package com.project.presentation.ui.view.register_food_establishment

import androidx.annotation.StringRes
import com.project.presentation.R
import com.project.presentation.ui.screens.add_food_establishments.model.FoodEstablishmentType

data class MainInfoViewState(
    val name: String? = null,
    val foodEstablishmentType: FoodEstablishmentType? = null,
    val address: String? = null,
    val description: String? = null
){

    @StringRes
    val nameLabelText: Int = R.string.name
}