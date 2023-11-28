package com.project.presentation.ui.screens.fedetailsforadmin.view

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.presentation.R

@Composable
fun TimeSlotDetailsItemView(
    modifier: Modifier = Modifier,
    viewState: TimeSlotDetailsViewState
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(2.dp, colorResource(id = R.color.gray)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_calendar),
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = null,

                    modifier = Modifier
                        .size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = viewState.date ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_clock),
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = viewState.timeSlot ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_user),
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = viewState.reservatorName ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_people),
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Кількість столів: ${viewState.tablesCounter.toString()}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_notes),
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = if (viewState.notes?.isNotEmpty() == true) {
                        "Замітки користувача: ${viewState.notes}"
                    } else {
                        "Користувач не залишив ніяких заміток"
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TimeSlotDetailsItemViewPreview() {
    TimeSlotDetailsItemView(
        viewState = TimeSlotDetailsViewState(
            date = "10 вересня",
            timeSlot = "10:00-10:30",
            reservatorName = "Петро",
            tablesCounter = 4,
            notes = "Місце коло вікна"
        )
    )
}