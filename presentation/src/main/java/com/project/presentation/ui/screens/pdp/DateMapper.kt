package com.project.presentation.ui.screens.pdp

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateMapper {

    fun mapDate(timeMillis: Long): String {
        val now = LocalDateTime.now()
        val dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val duration = Duration.between(dateTime, now)

        if (duration.toMinutes() < 1) {
            return "Just now"
        } else if (duration.toHours() < 1) {
            return "${duration.toMinutes()} mins ago"
        }

        val today = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 0, 0)
        val yesterday = today.minusDays(1)

        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.getDefault())
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

        return when {
            dateTime.isAfter(today) -> "${duration.toHours()} hours ago"
            dateTime.isAfter(yesterday) -> "Yesterday at ${dateTime.format(timeFormatter)}"
            else -> dateTime.format(dateFormatter).replaceFirst("^0".toRegex(), "")
        }
    }
}