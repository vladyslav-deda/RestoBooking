package com.project.presentation.ui.screens.add_food_establishments.model

enum class AddFoodEstablishmentStep(val title: String, val stepNumber: Int) {
    MainInfo("Main information", 1),
    Tables("Tables", 2),
    Photos("Photos", 3)
}

enum class FoodEstablishmentType(val title: String) {
    Cafe("Cafe"),
    Restaurant("Restaurant"),
    Pub("Pub"),
    Pizzeria("Pizzeria"),
    Bar("Bar")
}