package com.project.data.mapper

import android.annotation.SuppressLint
import android.net.Uri
import com.project.data.model.FoodEstablishmentDto
import com.project.domain.model.FoodEstablishment
import com.project.domain.model.Photo

@SuppressLint("NewApi")
object Mapper {

    fun FoodEstablishment.toDto(imageUrlList: List<String>): FoodEstablishmentDto {
        return FoodEstablishmentDto(
            id = this.id,
            name = this.name,
            foodEstablishmentType = this.foodEstablishmentType,
            address = this.address,
            description = this.description,
            photoUrlList = imageUrlList,
            ownerName = this.ownerName,
            city = this.city,
            selectedTimeFrom = this.selectedTimeFrom,
            selectedTimeTo = this.selectedTimeTo,
            phoneForBooking = this.phoneForBooking,
            tags = this.tags,
            comments = this.comments,
            tablesForBooking = this.tablesForBooking
        )
    }

    fun FoodEstablishmentDto.toDomain(): FoodEstablishment {
        var totalRatingCount = 0.0f
        comments.forEach {
            totalRatingCount += it.rating
        }
        val rating: Float = totalRatingCount / comments.size
        return FoodEstablishment(
            id = this.id,
            name = this.name,
            foodEstablishmentType = this.foodEstablishmentType,
            address = this.address,
            description = this.description,
            photoList = this.photoUrlList.mapIndexedNotNull { index, uri ->
                Photo(
                    index,
                    Uri.parse(uri)
                )
            },
            ownerName = this.ownerName,
            city = this.city,
            selectedTimeFrom = this.selectedTimeFrom,
            selectedTimeTo = this.selectedTimeTo,
            phoneForBooking = this.phoneForBooking,
            tags = this.tags,
            comments = this.comments,
            rating = rating,
            tablesForBooking = this.tablesForBooking
        )
    }
}