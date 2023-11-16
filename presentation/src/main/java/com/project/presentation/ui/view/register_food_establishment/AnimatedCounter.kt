package com.project.presentation.ui.view.register_food_establishment

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.presentation.R
import com.project.presentation.ui.screens.spalsh.SplashScreen

@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    onValueDecreaseClick: () -> Unit,
    onValueIncreaseClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onValueDecreaseClick) {
            Icon(
                painterResource(id = R.drawable.ic_minus),
                modifier = Modifier.size(80.dp),
                contentDescription = null,
                tint = colorResource(
                    id = R.color.main_yellow
                )
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Counter(count = count)

        Spacer(modifier = Modifier.width(20.dp))
        IconButton(onClick = onValueIncreaseClick) {
            Icon(
                painterResource(id = R.drawable.ic_plus),
                modifier = Modifier.size(80.dp),
                contentDescription = null,
                tint = colorResource(
                    id = R.color.main_yellow
                )
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Counter(
    modifier: Modifier = Modifier,
    count: Int,
) {
    var oldCount by remember {
        mutableIntStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }

            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { height -> height } + fadeIn() with
                                slideOutHorizontally { height -> -height } + fadeOut()
                    } else {
                        slideInHorizontally { height -> -height } + fadeIn() with
                                slideOutHorizontally { height -> height } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                },
                label = ""
            ) { char ->
                Text(
                    text = char.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 50.sp)
                )
            }
        }
    }
}

@Preview
@Composable
fun AnimatedCounterPreview() {
    AnimatedCounter(count = 4, onValueDecreaseClick = {  }) {

    }
}