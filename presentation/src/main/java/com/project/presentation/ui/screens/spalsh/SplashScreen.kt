package com.project.presentation.ui.screens.spalsh

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.presentation.R
import kotlinx.coroutines.delay

private const val SPLASH_LOADING_TIME = 3000L

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateLogin: () -> Unit,
) {

    SplashScreenContent(modifier)
    LaunchedEffect(Unit) {
        delay(SPLASH_LOADING_TIME)
        navigateLogin()
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
    SplashScreen(navigateLogin = {})
}
