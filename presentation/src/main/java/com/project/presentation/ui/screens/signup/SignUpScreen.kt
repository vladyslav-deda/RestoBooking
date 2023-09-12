package com.project.presentation.ui.screens.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.view.SignUpView
import com.project.presentation.ui.view.common.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    homeNavigate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isNavigateHome) {
        homeNavigate()
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
                painter = painterResource(R.drawable.dark_background_image),
                contentDescription = "background_image",
                contentScale = ContentScale.FillBounds
            )
            SignUpView(
                viewState = uiState.signUpState,
                onInputFieldFocusChanged = viewModel::onInputFieldFocusChanged,
                onNameTextChange = viewModel::onNameInputChanged,
                onSurnameTextChange = viewModel::onSurnameInputChanged,
                onEmailTextChange = viewModel::onEmailInputChanged,
                onPasswordTextChange = viewModel::onPasswordInputChanged,
                onDuplicatePasswordTextChange = viewModel::onDuplicatePasswordInputChanged,
                onPasswordVisibilityClick = viewModel::onPasswordVisibilityChanged,
                onDuplicatePasswordVisibilityClick = viewModel::onDuplicatePasswordVisibilityChanged,
                onRegisterClicked = {})
            if (uiState.isLoading) {
                LoadingView()
            }
        }
    }
}