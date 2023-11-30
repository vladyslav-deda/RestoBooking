package com.project.domain.repository

import com.project.domain.model.SurveyModel

object SurveyData {

    fun getData(): List<SurveyModel> {
        return listOf(
            SurveyModel(
                question = "Чи сподобалась Вам якість обслуговування у закладі?",
                answers = listOf("Так", "Ні")
            ),
            SurveyModel(
                question = "Чи хотіли б ви ще раз відвідати наш заклад?",
                answers = listOf("Так", "Ні")
            ),
            SurveyModel(
                question = "Що Вам сподобалось у закладі?",
                answers = listOf("Кухня", "Обслуговування", "Атмосфера", "Розташування", "Нічого")
            )
        )
    }
}