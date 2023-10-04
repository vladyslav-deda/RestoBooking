package com.project.presentation.ui.screens.add_food_establishments.model

enum class Tags(
    val title: String
) {
    SMOKING("Можна палити"),
    NON_SMOKING("Не можна курити"),
    PETS_ALLOWED("Можна з тваринами"),
    NO_PETS("Без тварин"),
    ADULTS_ONLY("Без дітей"),
    FAMILY_FRIENDLY("Сімейне місце"),
    LUNCH("Ланчі"),
    QUIET_ZONE("Тиха зона"),
    NO_ALCOHOL("Без алкоголю"),
    OUTDOOR_SEATING("Є столики надворі"),
    VEGETARIAN_OPTIONS("Вегетаріанські страви"),
    TERRACE("Тераса"),
    KARAOKE("Караоке"),
    LIVE_MUSIC("Жива музика")
}