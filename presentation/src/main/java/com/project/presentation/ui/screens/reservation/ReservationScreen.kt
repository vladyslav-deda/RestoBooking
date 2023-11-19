package com.project.presentation.ui.screens.reservation

import androidx.annotation.StringRes
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.view.common.LoadingView
import com.project.presentation.ui.view.register_food_establishment.AnimatedCounter
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
fun ReservationScreen(
    modifier: Modifier = Modifier,
    viewModel: ReservationScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val timeFromDialogState = rememberMaterialDialogState()
    val timeToDialogState = rememberMaterialDialogState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.resto_booking),
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black)
            )
        }
    ) { contentPadding ->
        Box(
            modifier = modifier
                .padding(contentPadding)
                .fillMaxSize(),
        ) {
            if (uiState.isLoading) {
                LoadingView()
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState())
                            .align(Alignment.TopCenter)
                    ) {
                        InfoMessage(messageRes = R.string.booking_message)
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            value = uiState.getFormattedTimeFrom()
                                ?: stringResource(R.string.start_time_booking),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    timeFromDialogState.show()
                                },
                            onValueChange = {},
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledTextColor = if (uiState.selectedTimeFrom != null) Color.Black
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
                        OutlinedTextField(
                            value = uiState.getFormattedTimeTo()
                                ?: stringResource(R.string.end_time_booking),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    timeToDialogState.show()
                                },
                            onValueChange = {},
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledTextColor = if (uiState.selectedTimeTo != null) Color.Black
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
                        InfoMessage(messageRes = R.string.people_number_message)
                        Spacer(modifier = Modifier.height(20.dp))
                        AnimatedCounter(
                            count = uiState.peopleCount,
                            onValueDecreaseClick = {
                                if (uiState.peopleCount > 0) {
                                    val newValue = uiState.peopleCount - 1
                                    viewModel.onPeopleCounterChanged(newValue)
                                }
                            },
                            onValueIncreaseClick = {
                                val newValue = uiState.peopleCount + 1
                                viewModel.onPeopleCounterChanged(newValue)
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        InfoMessage(messageRes = R.string.comment_message)
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            value = uiState.comment ?: "",
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = {
                                viewModel.onCommentChanged(it)
                            },
                            placeholder = { Text(text = stringResource(id = R.string.comment)) },
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
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        Divider(
                            color = colorResource(id = R.color.main_yellow),
                            thickness = 1.dp
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                        ) {
                            Button(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                onClick = {},
                                enabled = uiState.isFinishReservationButtonEnabled(),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(
                                        id = R.color.main_yellow
                                    )
                                )
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = "Завершити бронювання",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    MaterialDialog(
        dialogState = timeFromDialogState,
        buttons = {
            positiveButton(
                text = "Підтвердити",
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    colorResource(id = R.color.main_yellow)
                )
            )
            negativeButton(
                text = "Відмінити",
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    colorResource(id = R.color.dark_gray)
                )
            )
        }
    ) {
        val instantTimeFrom = Instant.ofEpochMilli(viewModel.retrieveWorkingTimeFrom())
        val initialTimeFrom =
            LocalDateTime.ofInstant(instantTimeFrom, ZoneId.systemDefault()).toLocalTime()
        val instantTimeTo = Instant.ofEpochMilli(viewModel.retrieveWorkingTimeTo())
        val initialTimeTo =
            LocalDateTime.ofInstant(instantTimeTo, ZoneId.systemDefault()).toLocalTime()

        timepicker(
            initialTime = initialTimeFrom,
            title = "Оберіть час почаку бронювання",
            timeRange = initialTimeFrom..initialTimeTo,
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
            viewModel.onStartTimeChanged(date.timeInMillis)
        }
    }
    MaterialDialog(
        dialogState = timeToDialogState,
        buttons = {
            positiveButton(
                text = "Підтвердити",
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    colorResource(id = R.color.main_yellow)
                )
            )
            negativeButton(
                text = "Відмінити",
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    colorResource(id = R.color.dark_gray)
                )
            )
        }
    ) {
        val instantTimeFrom =
            Instant.ofEpochMilli(uiState.selectedTimeFrom ?: viewModel.retrieveWorkingTimeFrom())
        val initialTimeFrom =
            LocalDateTime.ofInstant(instantTimeFrom, ZoneId.systemDefault()).toLocalTime()
        val modifiedInitialTimeFrom = if (uiState.selectedTimeFrom != null) {
            initialTimeFrom.plusMinutes(30)
        } else {
            initialTimeFrom
        }

        val instantTimeTo = Instant.ofEpochMilli(viewModel.retrieveWorkingTimeTo())
        val initialTimeTo =
            LocalDateTime.ofInstant(instantTimeTo, ZoneId.systemDefault()).toLocalTime()

        val interval = 30

        val timeRange: ClosedRange<LocalTime> = modifiedInitialTimeFrom..initialTimeTo
        val generatedTimes = mutableListOf<LocalTime>()

        var currentTime = modifiedInitialTimeFrom
        while (currentTime <= initialTimeTo) {
            generatedTimes.add(currentTime)
            currentTime = currentTime.plusMinutes(interval.toLong())
        }

        timepicker(
            initialTime = initialTimeFrom!!,
            title = "Вкажіть час закінчення бронювання",
            timeRange = timeRange.start..timeRange.endInclusive,
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

            viewModel.onEndTimeChanged(date.timeInMillis)
        }
    }
}

@Composable
private fun InfoMessage(
    modifier: Modifier = Modifier,
    @StringRes messageRes: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            tint = colorResource(id = R.color.main_yellow),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

