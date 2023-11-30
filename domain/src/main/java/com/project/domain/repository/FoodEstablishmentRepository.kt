package com.project.domain.repository

import com.project.domain.model.FoodEstablishment
import com.project.domain.model.StatisticModel

interface FoodEstablishmentRepository {

    suspend fun registerFoodEstablishment(foodEstablishment: FoodEstablishment): Result<Unit>

    suspend fun fetchFoodEstablishments(
        city: String = "",
        tags: List<String> = emptyList(),
        withTimeFilters: Boolean = false
    ): Result<List<FoodEstablishment>>

    suspend fun getFoodEstablishmentById(
        id: String
    ): Result<FoodEstablishment>

    suspend fun addComment(
        foodEstablishmentId: String,
        commentText: String,
        rating: Int
    ): Result<Unit>

    suspend fun getFoodEstablishmentOfCurrentUser(): Result<List<FoodEstablishment>>

    suspend fun addReplyForComment(
        foodEstablishmentId: String,
        commentId: String,
        replyText: String
    ): Result<Unit>

    suspend fun addStatisticsSurvey(
        foodEstablishmentId: String,
        statisticModel: StatisticModel
    ): Result<Unit>
}