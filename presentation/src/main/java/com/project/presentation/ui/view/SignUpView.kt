package com.project.presentation.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.project.presentation.R

@Composable
fun SignUpView(
    modifier: Modifier = Modifier,
    viewState: SignUpViewState,
    onInputFieldFocusChanged: (Boolean) -> Unit,
    onNameTextChange: (String) -> Unit,
    onSurnameTextChange: (String) -> Unit,
    onEmailTextChange: (String) -> Unit,
    onPasswordTextChange: (String) -> Unit,
    onDuplicatePasswordTextChange: (String) -> Unit,
    onPasswordVisibilityClick: () -> Unit,
    onDuplicatePasswordVisibilityClick: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = viewState.isInputFieldInFocus.not()) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.rb_label),
                contentDescription = "label_image",
            )
        }
        TextInputField(
            value = viewState.name,
            onInputFieldFocusChanged = onInputFieldFocusChanged,
            onValueChange = onNameTextChange,
            isError = viewState.errorMessage?.isNotEmpty() == true,
            labelText = stringResource(id = viewState.nameLabelText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            clearFocus = {
                focusManager.clearFocus()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextInputField(
            value = viewState.surname,
            onInputFieldFocusChanged = onInputFieldFocusChanged,
            onValueChange = onSurnameTextChange,
            isError = viewState.errorMessage?.isNotEmpty() == true,
            labelText = stringResource(id = viewState.surnameLabelText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            clearFocus = {
                focusManager.clearFocus()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}
