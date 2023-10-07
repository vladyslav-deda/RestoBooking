package com.project.presentation.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.project.domain.model.FoodEstablishmentType
import com.project.domain.model.Photo
import com.project.presentation.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SrpItemView(
    modifier: Modifier = Modifier,
    viewState: SrpItemViewState,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Card(
        modifier = modifier
            .padding(20.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, colorResource(id = R.color.main_yellow)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {

            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current).data(viewState.photo.uri)
                    .crossfade(enable = true).build(),
                contentDescription = "Avatar Image",
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = viewState.name,
                    style = MaterialTheme.typography.headlineMedium.copy(color = colorResource(id = R.color.main_yellow))
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Тип закладу: ${viewState.foodEstablishmentType.title}",
                    style = MaterialTheme.typography.bodyMedium.copy(colorResource(id = R.color.dark_gray))
                )
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                ) {
                    viewState.tags.forEach { tag ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier
                                .padding(
                                    vertical = 2.dp,
                                )
                                .border(
                                    width = 1.dp,
                                    color = colorResource(id = R.color.main_yellow),
                                    shape = shape
                                )
                                .background(
                                    color = colorResource(id = R.color.light_yellow),
                                    shape = shape
                                )
                                .clip(shape = shape)
                                .padding(6.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.bodySmall .copy(color = colorResource(id = R.color.dark_gray))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SrpItemViewPreview() {
    SrpItemView(
        viewState = SrpItemViewState(
            photo = Photo(),
            name = "Острівок",
            foodEstablishmentType = FoodEstablishmentType.Pizzeria,
            tags = listOf("test", "tags", "for", "preview")
        ),
        onClick = {}
    )
}