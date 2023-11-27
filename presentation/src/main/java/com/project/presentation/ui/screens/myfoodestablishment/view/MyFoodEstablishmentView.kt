package com.project.presentation.ui.screens.myfoodestablishment.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.presentation.R
import com.project.presentation.ui.view.RatingBar

@Composable
fun MyFoodEstablishmentItemView(
    modifier: Modifier = Modifier,
    viewState: MyFoodEstablishmentViewState,
    navigateToFoodEstablishmentDetailsForReservator: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                navigateToFoodEstablishmentDetailsForReservator()
            }
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = viewState.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = null
                )
            }
            if (viewState.rating > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                RatingBar(
                    rating = viewState.rating
                )
                Spacer(modifier = Modifier.height(12.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
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
            if (viewState.commentsWithoutAnswers > 0) {
                Text(
                    text = "Кількість користувачів, які не отримали зворотнього зв'язку: ${viewState.commentsWithoutAnswers}, виправте це якнайшвидше!",
                    style = MaterialTheme.typography.titleMedium.copy(color = colorResource(id = R.color.red))
                )
            } else {
                Text(
                    text = "Чудово! Усі користувачі отримали зворотній зв'язок",
                    style = MaterialTheme.typography.titleMedium.copy(color = colorResource(id = R.color.green))
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun MyFoodEstablishmentItemViewPreview() {
    MyFoodEstablishmentItemView(
        viewState = MyFoodEstablishmentViewState(
            id = "",
            name = "Ресторан \"Тест\"",
            rating = 4.6f,
            address = "Келецька 95б, Вінниця"
        ),
        navigateToFoodEstablishmentDetailsForReservator = {}
    )
}