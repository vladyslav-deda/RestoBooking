package com.project.presentation.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.project.presentation.R
import java.lang.Math.ceil
import java.lang.Math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = colorResource(
                id = (R.color.main_yellow)
            ))
        }
//        if (halfStar) {
//            Icon(
//                imageVector = Icons.Outlined.StarHalf,
//                contentDescription = null,
//                tint = colorResource(
//                    id = (R.color.main_yellow)
//                )
//            )
//        }
//        repeat(unfilledStars) {
//            Icon(
//                imageVector = Icons.Outlined.StarOutline,
//                contentDescription = null,
//                tint = colorResource(
//                    id = (R.color.main_yellow)
//                )
//            )
//        }
    }
}