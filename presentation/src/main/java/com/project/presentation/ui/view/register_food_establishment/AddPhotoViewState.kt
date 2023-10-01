package com.project.presentation.ui.view.register_food_establishment

import com.project.domain.model.Photo

data class AddPhotoViewState(
    val photoList: List<Photo> = emptyPhotoList
) {

    companion object {
        val emptyPhotoList: List<Photo> = buildList {
            for (i in 0..5) {
                this.add(
                    Photo(
                        index = i,
                        uri = null
                    )
                )
            }
        }
    }

}

