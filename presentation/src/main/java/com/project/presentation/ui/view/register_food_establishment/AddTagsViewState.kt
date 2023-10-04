package com.project.presentation.ui.view.register_food_establishment

import com.project.presentation.ui.screens.add_food_establishments.model.Tags

data class AddTagsViewState(
    val selectedTagsList: List<Tag> = emptyList(),
    val recommendedTagsList: List<Tag> = defaultTagsList,
    val addNewTagClicked:Boolean = false
) {

    fun isContinueButtonEnabled() = selectedTagsList.isNotEmpty()

    companion object {
        private val defaultTagsList = Tags.values().map {
            Tag(title = it.title)
        }
    }
}


data class Tag(
    val title: String = "",
    val isSelected: Boolean = false
)
