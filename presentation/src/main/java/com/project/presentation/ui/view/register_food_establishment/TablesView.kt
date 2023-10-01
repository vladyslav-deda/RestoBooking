package com.project.presentation.ui.view.register_food_establishment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            text = stringResource(R.string.specify_the_number_of_tables),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight(700)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.two_seater_table),
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
                text = stringResource(R.string.four_seater_table),
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
                text = stringResource(R.string.six_seater_table),
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
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow))
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 54.dp),
                text = stringResource(id = R.string.continue_label),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}