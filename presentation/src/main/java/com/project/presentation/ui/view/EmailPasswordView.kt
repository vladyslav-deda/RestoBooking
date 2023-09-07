package com.project.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
            .fillMaxWidth()
    ) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = viewState.email,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onEmailTextChange,
            label = { Text(text = stringResource(viewState.emailLabelText)) },
            isError = viewState.password.isError,
            placeholder = { Text(text = stringResource(viewState.emailLabelText)) },
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewState.password.password,
            modifier = modifier.fillMaxWidth(),
            onValueChange = onPasswordTextChange,
            label = { Text(text = stringResource(viewState.password.labelText)) },
            isError = viewState.password.isError,
            singleLine = true,
            placeholder = { Text(text = stringResource(viewState.password.labelText)) },
            visualTransformation = if (viewState.password.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                IconButton(onClick = { onPasswordVisibilityClick() }) {
                    Icon(
                        painterResource(id = viewState.password.endIcon()),
                        contentDescription = "passwarod visibility"
                    )
                }
            }
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
//        Button(
//            onClick = onSignInClicked,
//            enabled = viewState.isButtonEnabled(),
//            shape = CutCornerShape(10),
//            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow))
//        ) {
//            Text(
//                modifier = Modifier.padding(vertical = 8.dp, horizontal = 54.dp),
//                text = stringResource(id = R.string.sign_in),
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
    }
}