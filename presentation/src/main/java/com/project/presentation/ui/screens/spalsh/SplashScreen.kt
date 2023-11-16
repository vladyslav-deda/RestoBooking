package com.project.presentation.ui.screens.spalsh

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay

private const val SPLASH_LOADING_TIME = 10000L

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = hiltViewModel(),
    navigateLogin: () -> Unit,
    navigateHome: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SplashScreenContent(modifier)
    LaunchedEffect(Unit) {
        delay(SPLASH_LOADING_TIME)
        if (uiState.navigateHomeScreen) {
            navigateHome()
        } else if (uiState.navigateLoginScreen) {
            navigateLogin()
        }
    }
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.splash_image_light),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 80.dp),
            painter = painterResource(R.drawable.rb_label),
            contentDescription = "label_image",
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SplashPreview() {
    SplashScreen(navigateLogin = {}, navigateHome = {})
}
