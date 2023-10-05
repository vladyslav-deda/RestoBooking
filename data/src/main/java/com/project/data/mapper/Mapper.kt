package com.project.data.mapper

import android.net.Uri
import com.project.data.model.FoodEstablishmentDto
import com.project.domain.model.FoodEstablishment
import com.project.domain.model.Photo

class Mapper {

    fun foodEstablishmentToDto(foodEstablishment:FoodEstablishment, imageUrlList: List<String>) =
        FoodEstablishmentDto(
            name = foodEstablishment.name,
            foodEstablishmentType = foodEstablishment.foodEstablishmentType,
            address = foodEstablishment.address,
            description = foodEstablishment.description,
            photoUrlList = imageUrlList,
            ownerName = foodEstablishment.ownerName,
            city = foodEstablishment.city,
            selectedTimeFrom = foodEstablishment.selectedTimeFrom,
            selectedTimeTo = foodEstablishment.selectedTimeTo,
            phoneForBooking = foodEstablishment.phoneForBooking,
            tags = foodEstablishment.tags
        )

    fun foodEstablishmentDtoToDomain(foodEstablishmentDto:FoodEstablishmentDto) =
        FoodEstablishment(
            name = foodEstablishmentDto.name,
            foodEstablishmentType = foodEstablishmentDto.foodEstablishmentType,
            address = foodEstablishmentDto.address,
            description = foodEstablishmentDto.description,
            photoList = foodEstablishmentDto.photoUrlList.mapIndexedNotNull { index, uri -> Photo(index, Uri.parse(uri)) },
            ownerName = foodEstablishmentDto.ownerName,
            city = foodEstablishmentDto.city,
            selectedTimeFrom = foodEstablishmentDto.selectedTimeFrom,
            selectedTimeTo = foodEstablishmentDto.selectedTimeTo,
            phoneForBooking = foodEstablishmentDto.phoneForBooking,
            tags = foodEstablishmentDto.tags
        )
}