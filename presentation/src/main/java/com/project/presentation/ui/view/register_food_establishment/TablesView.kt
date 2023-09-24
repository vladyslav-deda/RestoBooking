package com.project.presentation.ui.view.register_food_establishment

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.presentation.R

@Composable
fun TablesView(
    modifier: Modifier = Modifier,
    viewState: TablesViewState,
    onTwoSeaterTableValueIncreased: () -> Unit,
    onTwoSeaterTableValueDecreased: () -> Unit,
    onFourSeaterTableValueChangedIncreased: () -> Unit,
    onFourSeaterTableValueChangedDecreased: () -> Unit,
    onSixSeaterTableValueChangedIncreased: () -> Unit,
    onSixSeaterTableValueChangedDecreased: () -> Unit,
    onContinueClicked: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Specify the number of tables",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight(700)
            )
        )
        Spacer(modifier = Modifier.height(45.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Two-seater table",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400)
                )
            )
            AnimatedCounter(
                count = viewState.twoSeaterTableValue,
                onValueIncreaseClick = onTwoSeaterTableValueIncreased,
                onValueDecreaseClick = onTwoSeaterTableValueDecreased
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Four-seater table",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400)
                )
            )
            AnimatedCounter(
                count = viewState.fourSeaterTableValue,
                onValueIncreaseClick = onFourSeaterTableValueChangedIncreased,
                onValueDecreaseClick = onFourSeaterTableValueChangedDecreased
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Six-seater table",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400)
                )
            )
            AnimatedCounter(
                count = viewState.sixSeaterTableValue,
                onValueIncreaseClick = onSixSeaterTableValueChangedIncreased,
                onValueDecreaseClick = onSixSeaterTableValueChangedDecreased
            )
        }
        Spacer(modifier = Modifier.height(45.dp))
        Button(
            onClick = onContinueClicked,
//            enabled = viewState.isContinueButtonEnabled(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow))
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 54.dp),
                text = stringResource(id = R.string.continue_label),
                style = MaterialTheme.typography.titleLarge
            )
        }
//        var count by remember {
//            mutableStateOf(0)
//        }
//
//        AnimatedCounter(
//            count = count,
//            onValueDecreaseClick = { if (count > 0) count-- },
//            onValueIncreaseClick = { count++ }
//        )
    }
}