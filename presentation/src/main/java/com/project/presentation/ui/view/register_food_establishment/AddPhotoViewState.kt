package com.project.presentation.ui.view.register_food_establishment

import android.net.Uri

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

data class Photo(
    val index: Int,
    val uri: Uri?
)