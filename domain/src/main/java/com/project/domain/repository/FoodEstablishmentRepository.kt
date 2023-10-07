package com.project.domain.repository

import com.project.domain.model.FoodEstablishment

interface FoodEstablishmentRepository {

    suspend fun registerFoodEstablishment(foodEstablishment: FoodEstablishment): Result<Unit>

    suspend fun fetchFoodEstablishments(
        city: String = "",
        tags: List<String> = emptyList()
    ): Result<List<FoodEstablishment>>
}