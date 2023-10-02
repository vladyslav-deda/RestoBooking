package com.project.presentation.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.project.presentation.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeSearchView(
    modifier: Modifier = Modifier,
    viewState: HomeSearchViewState,
    onCityChanged: (String) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    onNumberOfPersonsChanged: (Int) -> Unit,
    onSearchClicked: () -> Unit,
) {
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val focusManager = LocalFocusManager.current
    val currentDay = LocalDate.now()
    val allowedDates = listOf(currentDay, currentDay.plusDays(1), currentDay.plusDays(2))
    val currentTime = LocalTime.now()
    var expandedTypesMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewState.city,
            modifier = modifier
                .fillMaxWidth(),
            onValueChange = onCityChanged,
            placeholder = { Text(text = stringResource(id = R.string.city)) },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_place),
                    contentDescription = null,
                    tint = colorResource(
                        id = R.color.main_yellow
                    )
                )
            },
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
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewState.getFormattedDate() ?: stringResource(R.string.select_date),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    dateDialogState.show()
                },
            onValueChange = {},
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = colorResource(
                        id = R.color.main_yellow
                    )
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = if (viewState.selectedDate != null) Color.Black
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
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedVisibility(visible = viewState.selectedDate != null) {
            Column {
                OutlinedTextField(
                    value = viewState.getFormattedTime() ?: stringResource(R.string.select_time),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            timeDialogState.show()
                        },
                    onValueChange = {},
                    leadingIcon = {
                        Icon(
                            painterResource(id = R.drawable.ic_clock),
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.main_yellow
                            )
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = if (viewState.selectedTime != null) Color.Black
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
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = if (viewState.numberOfPersons > 0) stringResource(
                    R.string.persons,
                    viewState.numberOfPersons
                ) else
                    stringResource(R.string.number_of_persons),
                modifier = modifier
                    .clickable {
                        expandedTypesMenu = !expandedTypesMenu
                    }
                    .fillMaxWidth(),
                onValueChange = {},
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_user),
                        contentDescription = null,
                        tint = colorResource(
                            id = R.color.main_yellow
                        )
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.ArrowDropDown,
                        contentDescription = "",
                        tint = Color.Black
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = if (viewState.numberOfPersons > 0) Color.Black
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
                    viewState.getListOfPersonsNumber()
                        .forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(text = stringResource(R.string.persons, it))
                                },
                                onClick = {
                                    onNumberOfPersonsChanged(it)
                                    expandedTypesMenu = !expandedTypesMenu
                                }
                            )
                        }
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onSearchClicked,
            shape = RoundedCornerShape(8.dp),
            enabled = viewState.isSearchButtonEnabled(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow))
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 54.dp),
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.titleLarge
            )
        }
        MaterialDialog(
            dialogState = dateDialogState,
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
            datepicker(
                initialDate = LocalDate.now(),
                title = "Select date of the reservation",
                allowedDateValidator = {
                    allowedDates.contains(it)
                },
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = Color.Black,
                    dateActiveBackgroundColor = colorResource(id = R.color.main_yellow),
                )
            ) {
                onDateSelected(it)
            }
        }
        MaterialDialog(
            dialogState = timeDialogState,
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
                initialTime = currentTime,
                title = "Select time of the reservation",
                timeRange = if (viewState.selectedDate == currentDay) currentTime..LocalTime.MAX else LocalTime.MIDNIGHT..LocalTime.MAX,
                is24HourClock = true,
                colors = TimePickerDefaults.colors(
                    activeBackgroundColor = colorResource(id = R.color.main_yellow),
                    inactivePeriodBackground = colorResource(id = R.color.gray),
                    selectorColor = colorResource(id = R.color.main_yellow),
                    selectorTextColor = Color.White,
                    borderColor = Color.Red,
                    activeTextColor = Color.White,
                    inactiveTextColor = colorResource(id = R.color.dark_gray),
                    inactiveBackgroundColor = colorResource(id = R.color.gray)
                )
            ) {
                onTimeSelected(it)
            }
        }
    }
}