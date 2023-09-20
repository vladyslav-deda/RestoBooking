package com.project.presentation.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.presentation.R

@Composable
fun EmailPasswordView(
    modifier: Modifier = Modifier,
    viewState: EmailPasswordViewState,
    onEmailTextChange: (String) -> Unit,
    onPasswordTextChange: (String) -> Unit,
    onPasswordVisibilityClick: () -> Unit,
    onSignInClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        TextInputField(
            value = viewState.email,
            onValueChange = onEmailTextChange,
            isError = viewState.errorMessage?.isNotEmpty() == true,
            labelText = stringResource(viewState.emailLabelText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        ) {
            focusManager.clearFocus()
        }
        Spacer(modifier = Modifier.height(20.dp))
        PasswordInputField(
            value = viewState.password.password,
            onValueChange = onPasswordTextChange,
            isError = viewState.errorMessage?.isNotEmpty() == true,
            labelText = viewState.password.labelText,
            showPassword = viewState.password.showPassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            endIcon = viewState.password.endIcon(),
            clearFocus = { focusManager.clearFocus() },
            onPasswordVisibilityClick = onPasswordVisibilityClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedVisibility(viewState.errorMessage?.isNotEmpty() == true) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewState.errorMessage ?: "",
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.error),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        Button(
            onClick = onSignInClicked,
//            enabled = viewState.isButtonEnabled(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow))
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 54.dp),
                text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    value: String,
    onInputFieldFocusChanged: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    labelText: String,
    keyboardOptions: KeyboardOptions,
    clearFocus: () -> Unit
) {
    TextField(
        value = value,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                onInputFieldFocusChanged(it.isFocused)
            },
        onValueChange = onValueChange,
        isError = isError,
        placeholder = { Text(text = labelText) },
        keyboardActions = KeyboardActions(onNext = { clearFocus() }),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            cursorColor = colorResource(id = R.color.gray),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = Color.Black,
            placeholderColor = colorResource(id = R.color.gray)
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    value: String,
    onInputFieldFocusChanged: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    isError: Boolean,
    @StringRes
    labelText: Int,
    showPassword: Boolean,
    keyboardOptions: KeyboardOptions,
    @DrawableRes
    endIcon: Int,
    clearFocus: () -> Unit,
    onPasswordVisibilityClick: () -> Unit
) {
    TextField(
        value = value,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                onInputFieldFocusChanged(it.isFocused)
            },
        onValueChange = onValueChange,
        isError = isError,
        singleLine = true,
        placeholder = { Text(text = stringResource(id = labelText)) },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(onNext = { clearFocus() }),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            cursorColor = colorResource(id = R.color.gray),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = Color.Black,
            placeholderColor = colorResource(id = R.color.gray)
        ),
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            IconButton(onClick = { onPasswordVisibilityClick() }) {
                Icon(
                    painterResource(id = endIcon),
                    contentDescription = "password visibility"
                )
            }
        },
    )
}

@Preview(
    showBackground = true
)
@Composable
fun EmailPasswordViewPreview() {
    EmailPasswordView(
        viewState = EmailPasswordViewState(
            email = "test",
            password = PasswordViewState(password = "test")
        ),
        onEmailTextChange = {},
        onPasswordTextChange = {},
        onPasswordVisibilityClick = {},
        onSignInClicked = {}
    )
}