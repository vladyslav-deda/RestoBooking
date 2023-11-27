package com.project.presentation.ui.screens.myreservations.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.presentation.R

@Composable
fun MyReservationItemView(
    modifier: Modifier = Modifier,
    viewState: MyReservationItemViewState,
    navigateToPdp: () -> Unit,
    removeReservation: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                navigateToPdp()
            }
            .fillMaxWidth(),
        border = BorderStroke(2.dp, colorResource(id = R.color.gray)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = viewState.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = colorResource(id = R.color.main_yellow)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = viewState.date,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_place),
                    contentDescription = null,
                    tint = colorResource(id = R.color.main_yellow)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = viewState.address,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = removeReservation) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "Відмінити бронювання",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Red)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun MyReservationItemViewPreview() {
    MyReservationItemView(
        viewState = MyReservationItemViewState(
            foodEstablishmentId = "",
            name = "Ресторан \"Тест\"",
            date = "10 вересня 13:00 - 16:00",
            address = "Келецька 95б, Вінниця"
        ),
        navigateToPdp = {},
        removeReservation = {}
    )
}