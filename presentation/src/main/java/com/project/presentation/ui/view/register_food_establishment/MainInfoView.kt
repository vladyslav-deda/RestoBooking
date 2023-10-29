package com.project.presentation.ui.view.register_food_establishment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.project.domain.model.FoodEstablishmentType
import com.project.presentation.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainInfoView(
    modifier: Modifier = Modifier,
    viewState: MainInfoViewState,
    onNameChanged: (String) -> Unit,
    onFoodEstablishmentTypeChanged: (FoodEstablishmentType) -> Unit,
    onAddressChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onContinueClicked: () -> Unit,
    onFromTimeSelected: (Long) -> Unit,
    onToTimeSelected: (Long) -> Unit,
    onPhoneForReservationChanged: (String) -> Unit
) {
    val timeFromDialogState = rememberMaterialDialogState()
    val timeToDialogState = rememberMaterialDialogState()
    val focusManager = LocalFocusManager.current
    var expandedTypesMenu by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewState.name ?: "",
            modifier = modifier
                .fillMaxWidth(),
            onValueChange = onNameChanged,
            placeholder = { Text(text = stringResource(id = viewState.nameLabelText)) },
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = colorResource(id = R.color.gray),
                textColor = Color.Black,
                placeholderColor = colorResource(id = R.color.gray),
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = viewState.city ?: "",
            modifier = modifier
                .fillMaxWidth(),
            onValueChange = onCityChanged,
            placeholder = { Text(text = stringResource(id = viewState.cityLabelText)) },
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = colorResource(id = R.color.gray),
                textColor = Color.Black,
                placeholderColor = colorResource(id = R.color.gray),
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = viewState.address ?: "",
            modifier = modifier
                .fillMaxWidth(),
            onValueChange = onAddressChanged,
            placeholder = { Text(text = stringResource(id = viewState.addressLabelText)) },
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = colorResource(id = R.color.gray),
                textColor = Color.Black,
                placeholderColor = colorResource(id = R.color.gray),
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = viewState.phoneForReservation ?: "",
            modifier = modifier
                .fillMaxWidth(),
            onValueChange = onPhoneForReservationChanged,
            placeholder = { Text(text = stringResource(id = viewState.phoneForReservationLabelText)) },
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = colorResource(id = R.color.gray),
                textColor = Color.Black,
                placeholderColor = colorResource(id = R.color.gray),
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = viewState.description ?: "",
            modifier = modifier
                .fillMaxWidth(),
            onValueChange = onDescriptionChanged,
            placeholder = { Text(text = stringResource(id = viewState.descriptionLabelText)) },
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = colorResource(id = R.color.gray),
                textColor = Color.Black,
                placeholderColor = colorResource(id = R.color.gray),
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewState.foodEstablishmentType?.title
                    ?: stringResource(R.string.food_establishment_type),
                modifier = modifier
                    .clickable {
                        expandedTypesMenu = !expandedTypesMenu
                    }
                    .fillMaxWidth(),
                onValueChange = {},

                trailingIcon = {
                    Icon(
                        Icons.Outlined.ArrowDropDown,
                        contentDescription = "",
                        tint = Color.Black
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = if (viewState.foodEstablishmentType?.title?.isNotEmpty() == true) Color.Black
                    else colorResource(id = R.color.gray),
                    placeholderColor = colorResource(id = R.color.gray),
                    disabledBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                enabled = false
            )
            MaterialTheme(
                shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
            ) {
                DropdownMenu(
                    modifier = Modifier
                        .background(Color.White),
                    expanded = expandedTypesMenu,
                    onDismissRequest = { expandedTypesMenu = false }
                ) {
                    FoodEstablishmentType.values()
                        .filter { it.title != null }
                        .forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it.title!!)
                                },
                                onClick = {
                                    onFoodEstablishmentTypeChanged(it)
                                    expandedTypesMenu = !expandedTypesMenu
                                }
                            )
                        }
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row {
            OutlinedTextField(
                value = viewState.getFormattedFromTime()
                    ?: stringResource(R.string.select_time_from),
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        timeFromDialogState.show()
                    },
                onValueChange = {},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = if (viewState.selectedTimeFrom != null) Color.Black
                    else colorResource(id = R.color.gray),
                    placeholderColor = colorResource(id = R.color.gray),
                    disabledBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                enabled = false
            )
            Spacer(modifier = Modifier.width(20.dp))
            OutlinedTextField(
                value = viewState.getFormattedToTime()
                    ?: stringResource(R.string.select_time_to),
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewState.selectedTimeFrom?.let {
                            timeToDialogState.show()
                        }
                    },
                onValueChange = {},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = if (viewState.selectedTimeTo != null) Color.Black
                    else colorResource(id = R.color.gray),
                    placeholderColor = colorResource(id = R.color.gray),
                    disabledBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                enabled = false
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = onContinueClicked,
//            enabled = viewState.isContinueButtonEnabled(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow))
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 54.dp),
                text = stringResource(id = R.string.continue_label),
                style = MaterialTheme.typography.titleLarge
            )
        }

        MaterialDialog(
            dialogState = timeFromDialogState,
            buttons = {
                positiveButton(
                    text = "Ok",
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        colorResource(id = R.color.main_yellow)
                    )
                )
                negativeButton(
                    text = "Cancel",
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        colorResource(id = R.color.dark_gray)
                    )
                )
            }
        ) {
            timepicker(
                initialTime = LocalTime.MIDNIGHT,
                title = "Specify the time when the establishment opens",
//                timeRange = if (viewState.selectedDate == currentDay) currentTime..LocalTime.MAX else LocalTime.MIDNIGHT..LocalTime.MAX,
                is24HourClock = true,
                colors = TimePickerDefaults.colors(
                    activeBackgroundColor = colorResource(id = R.color.main_yellow),
                    inactivePeriodBackground = colorResource(id = R.color.gray),
                    selectorColor = colorResource(id = R.color.main_yellow),
                    selectorTextColor = Color.White,
                    activeTextColor = Color.White,
                    inactiveTextColor = colorResource(id = R.color.dark_gray),
                    inactiveBackgroundColor = colorResource(id = R.color.light_gray)
                )
            ) {
                val date = Calendar.getInstance()
                date.set(Calendar.HOUR_OF_DAY, it.hour)
                date.set(Calendar.MINUTE, it.minute)
                onFromTimeSelected(date.timeInMillis)
            }
        }
        MaterialDialog(
            dialogState = timeToDialogState,
            buttons = {
                positiveButton(
                    text = "Ok",
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        colorResource(id = R.color.main_yellow)
                    )
                )
                negativeButton(
                    text = "Cancel",
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        colorResource(id = R.color.dark_gray)
                    )
                )
            }
        ) {
            val instant = Instant.ofEpochMilli(viewState.selectedTimeFrom!!)
            val initialTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime()
            timepicker(
                initialTime = initialTime!!,
                title = "Specify the time when the establishment closes",
                timeRange = initialTime..LocalTime.MAX,
                is24HourClock = true,
                colors = TimePickerDefaults.colors(
                    activeBackgroundColor = colorResource(id = R.color.main_yellow),
                    inactivePeriodBackground = colorResource(id = R.color.gray),
                    selectorColor = colorResource(id = R.color.main_yellow),
                    selectorTextColor = Color.White,
                    activeTextColor = Color.White,
                    inactiveTextColor = colorResource(id = R.color.dark_gray),
                    inactiveBackgroundColor = colorResource(id = R.color.gray),
                )
            ) {
                val date = Calendar.getInstance()
                date.set(Calendar.HOUR_OF_DAY, it.hour)
                date.set(Calendar.MINUTE, it.minute)

                onToTimeSelected(date.timeInMillis)
            }
        }
    }
}