package com.project.presentation.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.view.EmailPasswordView
import com.project.presentation.ui.view.common.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    homeNavigate: () -> Unit,
    signUpNavigate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isNavigateHome) {
        homeNavigate()
        return
    }

    if (uiState.isNavigateSignUp) {
        signUpNavigate()
        viewModel.resetSignUpStatus()
        return
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.dark_background_image),
                contentDescription = "background_image",
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(R.drawable.rb_label),
                    contentDescription = "label_image",
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.2f))
                EmailPasswordView(
                    viewState = uiState.emailPassword,
                    onPasswordTextChange = { viewModel.onPasswordInputChanged(it) },
                    onEmailTextChange = { viewModel.onEmailInputChanged(it) },
                    onPasswordVisibilityClick = { viewModel.onPasswordVisibilityChanged() },
                    onSignInClicked = { }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .clickable {
                            signUpNavigate()
                        },
                    text = stringResource(R.string.go_to_sign_up),
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White,textDecoration = TextDecoration.Underline)
                )
            }

            if (uiState.isLoading) {
                LoadingView()
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun LoginPreview() {
    LoginScreen(homeNavigate = {}, signUpNavigate = {})
}