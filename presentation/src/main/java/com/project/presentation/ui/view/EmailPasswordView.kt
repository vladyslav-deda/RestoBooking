package com.project.presentation.ui.view

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
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
        TextField(
            value = viewState.email,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onEmailTextChange,
            isError = viewState.password.isError,
            placeholder = { Text(text = stringResource(viewState.emailLabelText)) },
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
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
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = viewState.password.password,
            modifier = modifier.fillMaxWidth(),
            onValueChange = onPasswordTextChange,
            isError = viewState.password.isError,
            singleLine = true,
            placeholder = { Text(text = stringResource(viewState.password.labelText)) },
            visualTransformation = if (viewState.password.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
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
                        painterResource(id = viewState.password.endIcon()),
                        contentDescription = "password visibility"
                    )
                }
            },

            )
        Spacer(modifier = Modifier.height(20.dp))
        if (viewState.password.isError) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewState.errorMessage ?: "",
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.error),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
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