package com.project.presentation.ui.view.register_food_establishment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.project.domain.model.FoodEstablishmentType
import com.project.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainInfoView(
    modifier: Modifier = Modifier,
    viewState: MainInfoViewState,
    onNameChanged: (String) -> Unit,
    onFoodEstablishmentTypeChanged: (FoodEstablishmentType) -> Unit,
    onAddressChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onContinueClicked: () -> Unit
) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewState.foodEstablishmentType?.title ?: stringResource(R.string.food_establishment_type),
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
                    FoodEstablishmentType.values().forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = it.title)
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
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )
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
    }
}