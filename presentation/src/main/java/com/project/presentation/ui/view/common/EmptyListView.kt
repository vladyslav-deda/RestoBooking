package com.project.presentation.ui.view.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.project.presentation.R

@Composable
fun EmptyListView(
    modifier: Modifier = Modifier,
    text: String
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.reservations_list_empty))
    val logoAnimationState =
        animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(
                color = colorResource(id = R.color.dark_gray)
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Preview
@Composable
fun EmptyListViewPreview() {
    EmptyListView(text = "Не знайдено нічого")
}