package com.project.presentation.ui.screens.add_food_establishments.model

enum class AddFoodEstablishmentStep(val title: String, val stepNumber: Int) {
    AddFoodEstablishmentMainInfo("Головна інформація", 1),
    AddFoodEstablishmentTags("Теги", 2),
    AddFoodEstablishmentTablesCount("Кількість столиків", 3),
    AddFoodEstablishmentPhotos("Фото", 4)
}