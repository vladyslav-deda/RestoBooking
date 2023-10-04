package com.project.presentation.ui.view.register_food_establishment

import androidx.annotation.StringRes
import com.project.domain.model.FoodEstablishmentType
import com.project.presentation.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class MainInfoViewState(
    val name: String? = null,
    val city: String? = null,
    val address: String? = null,
    val description: String? = null,
    val phoneForReservation: String? = null,
    val foodEstablishmentType: FoodEstablishmentType? = null,
    val selectedTimeFrom: LocalTime? = null,
    val selectedTimeTo: LocalTime? = null,
) {

    @StringRes
    val nameLabelText: Int = R.string.name

    @StringRes
    val addressLabelText: Int = R.string.address

    @StringRes
    val descriptionLabelText: Int = R.string.description

    @StringRes
    val cityLabelText: Int = R.string.city

    @StringRes
    val phoneForReservationLabelText: Int = R.string.phone_for_reservation


    fun isContinueButtonEnabled() =
        name?.isNotEmpty() == true && foodEstablishmentType != null && address?.isNotEmpty() == true && description?.isNotEmpty() == true

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun getFormattedFromTime() = selectedTimeFrom?.format(timeFormatter)

    fun getFormattedToTime() = selectedTimeTo?.format(timeFormatter)
}