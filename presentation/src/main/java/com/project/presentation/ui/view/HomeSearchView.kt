package com.project.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.project.presentation.R
import com.project.presentation.ui.view.register_food_establishment.Tag
import com.project.presentation.ui.view.register_food_establishment.TagView
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun HomeSearchView(
    modifier: Modifier = Modifier,
    viewState: HomeSearchViewState,
    onCityChanged: (String) -> Unit,
    handleTagClick: (Tag) -> Unit,
    onSearchClicked: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val dateDialogState = rememberMaterialDialogState()
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
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewState.getFormattedDate()
                ?: stringResource(R.string.date_of_booking),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    dateDialogState.show()
                },
            onValueChange = {},
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
            enabled = false,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = colorResource(
                        id = R.color.main_yellow
                    )
                )
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                tint = colorResource(id = R.color.main_yellow),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = stringResource(R.string.chose_tags),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .padding(
                    vertical = 2.dp,
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(shape = RoundedCornerShape(20.dp))
                .padding(10.dp)
                .align(Alignment.Start)
                .fillMaxSize()
                .weight(1f)
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            ) {
                viewState.tags.forEach { tag ->
                    TagView(
                        tag = tag,
                        onClicked = {
                            handleTagClick(tag)
                        }
                    )
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
        Spacer(modifier = Modifier.height(80.dp))

        MaterialDialog(
            dialogState = dateDialogState,
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
            datepicker(
                initialDate = LocalDate.now(),
                title = "Оберіть дату бронювання",
                waitForPositiveButton = true,
                allowedDateValidator = {
                    val now = LocalDate.now()
                    val oneWeekForward = LocalDate.now().plusDays(7)
                    it.dayOfMonth >= now.dayOfMonth && it.dayOfMonth < oneWeekForward.dayOfMonth
                },
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = colorResource(id = R.color.main_yellow),
                    headerTextColor = Color.White,
                    dateActiveBackgroundColor = colorResource(id = R.color.main_yellow),
                    dateInactiveTextColor = colorResource(id = R.color.dark_gray),
                )
            ) {
                val date = Calendar.getInstance()
                date.set(Calendar.DAY_OF_YEAR, it.dayOfYear)
                date.set(Calendar.MONTH, it.monthValue)
                date.set(Calendar.YEAR, it.year)
                onDateSelected(date.timeInMillis)
            }
        }
    }
}