package com.project.domain.repository

import com.project.domain.model.FoodEstablishment

interface FoodEstablishmentRepository {

    suspend fun registerFoodEstablishment(foodEstablishment: FoodEstablishment): Result<Unit>

    suspend fun fetchFoodEstablishments(
        city: String = "",
        tags: List<String> = emptyList(),
        withTimeFilters:Boolean = false
    ): Result<List<FoodEstablishment>>

    suspend fun getFoodEstablishmentById(
        id: String
    ): Result<FoodEstablishment>

    suspend fun addComment(
        foodEstablishmentId: String,
        commentText: String,
        rating: Int
    ): Result<Unit>
}