package com.project.presentation.ui.screens.add_food_establishments.model

enum class AddFoodEstablishmentStep(val title: String, val stepNumber: Int) {
    AddFoodEstablishmentMainInfo("Головна інформація", 1),
    AddFoodEstablishmentTables("Теги", 2),
    AddFoodEstablishmentPhotos("Фото", 3)
}