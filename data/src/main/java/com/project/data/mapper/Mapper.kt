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
            twoSeaterTableValue = foodEstablishment.twoSeaterTableValue,
            fourSeaterTableValue = foodEstablishment.fourSeaterTableValue,
            sixSeaterTableValue = foodEstablishment.sixSeaterTableValue,
            photoUrlList = imageUrlList,
            ownerName = foodEstablishment.ownerName,
            city = foodEstablishment.city,
            selectedTimeFrom = foodEstablishment.selectedTimeFrom,
            selectedTimeTo = foodEstablishment.selectedTimeTo
        )

    fun foodEstablishmentDtoToDomain(foodEstablishmentDto:FoodEstablishmentDto) =
        FoodEstablishment(
            name = foodEstablishmentDto.name,
            foodEstablishmentType = foodEstablishmentDto.foodEstablishmentType,
            address = foodEstablishmentDto.address,
            description = foodEstablishmentDto.description,
            twoSeaterTableValue = foodEstablishmentDto.twoSeaterTableValue,
            fourSeaterTableValue = foodEstablishmentDto.fourSeaterTableValue,
            sixSeaterTableValue = foodEstablishmentDto.sixSeaterTableValue,
            photoList = foodEstablishmentDto.photoUrlList.mapIndexedNotNull { index, uri -> Photo(index, Uri.parse(uri)) },
            ownerName = foodEstablishmentDto.ownerName,
            city = foodEstablishmentDto.city,
            selectedTimeFrom = foodEstablishmentDto.selectedTimeFrom,
            selectedTimeTo = foodEstablishmentDto.selectedTimeTo
        )
}