package com.project.presentation.ui.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.project.presentation.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsView(
    modifier: Modifier = Modifier,
    tags: List<String>
) {
    val shape = RoundedCornerShape(20.dp)
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(
            6.dp,
            Alignment.CenterVertically
        ),
    ) {
        tags.forEach { tag ->
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
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colorResource(
                            id = R.color.dark_gray
                        )
                    )
                )
            }
        }
    }
}