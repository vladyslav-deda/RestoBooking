package com.project.presentation.ui.view.register_food_establishment

import androidx.annotation.StringRes
import com.project.domain.model.FoodEstablishmentType
import com.project.presentation.R
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

data class MainInfoViewState(
    val name: String? = null,
    val city: String? = null,
    val address: String? = null,
    val description: String? = null,
    val phoneForReservation: String? = null,
    val foodEstablishmentType: FoodEstablishmentType? = null,
    val selectedTimeFrom: Date? = null,
    val selectedTimeTo: Date? = null,
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

    private var simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun getFormattedFromTime() = selectedTimeFrom?.let { simpleDateFormat.format(it) }

    fun getFormattedToTime() = selectedTimeTo?.let { simpleDateFormat.format(it) }
}