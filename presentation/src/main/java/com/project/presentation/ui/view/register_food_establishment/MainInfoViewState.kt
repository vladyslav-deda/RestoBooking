package com.project.presentation.ui.view.register_food_establishment

import androidx.annotation.StringRes
import com.project.domain.model.FoodEstablishmentType
import com.project.presentation.R

data class MainInfoViewState(
    val name: String? = null,
    val foodEstablishmentType: FoodEstablishmentType? = null,
    val address: String? = null,
    val description: String? = null,
    val city: String? = null,
) {

    @StringRes
    val nameLabelText: Int = R.string.name

    @StringRes
    val addressLabelText: Int = R.string.address

    @StringRes
    val descriptionLabelText: Int = R.string.description

    @StringRes
    val cityLabelText: Int = R.string.city

    fun isContinueButtonEnabled() =
        name?.isNotEmpty() == true && foodEstablishmentType != null && address?.isNotEmpty() == true && description?.isNotEmpty() == true
}