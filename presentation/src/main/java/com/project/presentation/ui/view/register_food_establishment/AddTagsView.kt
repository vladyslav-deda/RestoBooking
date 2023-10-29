package com.project.presentation.ui.view.register_food_establishment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.presentation.R
import com.project.presentation.ui.screens.add_food_establishments.model.Tags
import com.project.presentation.ui.view.AddTagDialog

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddTagsView(
    modifier: Modifier = Modifier,
    viewState: AddTagsViewState,
    addTagToTheList: (Tag) -> Unit,
    removeTagFromTheList: (Tag) -> Unit,
    onContinueClicked: () -> Unit,
    setValueForAddNewTagClicked: (Boolean) -> Unit
) {

    if (viewState.addNewTagClicked) {
        AddTagDialog(
            onDismissDialog = { setValueForAddNewTagClicked(false) },
            onAddClicked = {
                val newTag = Tag(title = it)
                addTagToTheList(newTag)
            })
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                tint = colorResource(id = R.color.main_yellow),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = stringResource(R.string.add_tags_message),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(visible = viewState.selectedTagsList.isNotEmpty()) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.selected_tags),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                ) {
                    viewState.selectedTagsList.forEach { tag ->
                        TagView(
                            tag = tag,
                            onClicked = {
                                removeTagFromTheList(tag)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.recommended_tags),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        ) {
            viewState.recommendedTagsList.forEach { tag ->
                TagView(
                    tag = tag,
                    onClicked = {
                        addTagToTheList(tag)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(
                    vertical = 2.dp,
                )
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.main_yellow),
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = White,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable {
                    setValueForAddNewTagClicked(true)
                }
                .padding(10.dp)
                .align(Alignment.Start)
        ) {
            Icon(
                Icons.Default.Create,
                tint = colorResource(id = R.color.main_yellow),
                contentDescription = null
            )
            Text(
                text = "Створити тег",
                color = colorResource(id = R.color.main_yellow),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
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

@Composable
fun TagView(
    modifier: Modifier = Modifier,
    tag: Tag,
    onClicked: (Boolean) -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
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
                color = if (tag.isSelected) colorResource(id = R.color.light_yellow) else White,
                shape = shape
            )
            .clip(shape = shape)
            .clickable {
                onClicked(!tag.isSelected)
            }
            .padding(6.dp)
    ) {
        val image = if (tag.isSelected) Icons.Default.Close else Icons.Default.Add
        Icon(
            image,
            modifier = Modifier.size(15.dp),
            tint = colorResource(id = R.color.main_yellow),
            contentDescription = null
        )
        Text(
            text = tag.title,
            color = Color.Black,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
fun TagViewPreview() {
    TagView(tag = Tag(title = Tags.KARAOKE.title, isSelected = true), onClicked = {})
}