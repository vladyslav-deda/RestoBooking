package com.project.presentation.ui.screens.add_food_establishments.model

enum class AddFoodEstablishmentStep(val title: String, val stepNumber: Int) {
    AddFoodEstablishmentMainInfo("Main information", 1),
    AddFoodEstablishmentTables("Tables", 2),
    AddFoodEstablishmentPhotos("Photos", 3)
}

enum class FoodEstablishmentType(val title: String) {
    Cafe("Cafe"),
    Restaurant("Restaurant"),
    Pub("Pub"),
    Pizzeria("Pizzeria"),
    Bar("Bar")
}