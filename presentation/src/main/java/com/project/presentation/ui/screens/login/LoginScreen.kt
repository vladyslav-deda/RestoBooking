package com.project.presentation.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.screens.spalsh.SplashScreen
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
            modifier = modifier
                .padding(contentPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.splash_image_light),
                contentDescription = "background_image",
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailPasswordView(
                    viewState = uiState.emailPassword,
                    onPasswordTextChange = { viewModel.onPasswordInputChanged(it) },
                    onEmailTextChange = { viewModel.onEmailInputChanged(it) },
                    onPasswordVisibilityClick = { viewModel.onPasswordVisibilityChanged() },
                    onSignInClicked = { }
                )
                Spacer(modifier = Modifier.height(20.dp))
//                SignUpText(onClick = { viewModel.onSignUpClick() })
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