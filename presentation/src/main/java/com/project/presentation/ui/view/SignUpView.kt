package com.project.presentation.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.presentation.R

@Composable
fun SignUpView(
    modifier: Modifier = Modifier,
    viewState: SignUpViewState,
    onInputFieldFocusChanged: (Boolean) -> Unit,
    onNameAndSurnameInputChanged: (String) -> Unit,
    onEmailTextChange: (String) -> Unit,
    onPasswordTextChange: (String) -> Unit,
    onDuplicatePasswordTextChange: (String) -> Unit,
    onPasswordVisibilityClick: () -> Unit,
    onDuplicatePasswordVisibilityClick: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    if (viewState.isInputFieldInFocus.not()){
        focusManager.clearFocus()
    }
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
            value = viewState.nameAndSurname,
            onInputFieldFocusChanged = onInputFieldFocusChanged,
            onValueChange = onNameAndSurnameInputChanged,
            isError = viewState.errorMessage?.isNotEmpty() == true,
            labelText = stringResource(id = viewState.nameAndSurnameLabelText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            clearFocus = {
                focusManager.clearFocus()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextInputField(
            value = viewState.email,
            onInputFieldFocusChanged = onInputFieldFocusChanged,
            onValueChange = onEmailTextChange,
            isError = viewState.errorMessage?.isNotEmpty() == true,
            labelText = stringResource(id = viewState.emailLabelText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            clearFocus = {
                focusManager.clearFocus()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        PasswordInputField(
            value = viewState.password.password,
            onInputFieldFocusChanged = onInputFieldFocusChanged,
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
        PasswordInputField(
            value = viewState.duplicatePassword.password,
            onInputFieldFocusChanged = onInputFieldFocusChanged,
            onValueChange = onDuplicatePasswordTextChange,
            isError = viewState.errorMessage?.isNotEmpty() == true,
            labelText = viewState.duplicatePassword.labelText,
            showPassword = viewState.duplicatePassword.showPassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            endIcon = viewState.duplicatePassword.endIcon(),
            clearFocus = { focusManager.clearFocus() },
            onPasswordVisibilityClick = onDuplicatePasswordVisibilityClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedVisibility(viewState.errorMessage?.isNotEmpty() == true) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewState.errorMessage ?: "",
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.error),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onRegisterClicked,
//            enabled = viewState.isButtonEnabled(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow))
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 54.dp),
                text = stringResource(id = R.string.sign_up),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
