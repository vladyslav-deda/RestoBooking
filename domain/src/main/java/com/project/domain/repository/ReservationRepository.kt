package com.project.domain.repository

import com.project.domain.model.Reservation
import com.project.domain.model.TimeSlot

interface ReservationRepository {

    suspend fun addReservation(
        foodEstablishmentId: String,
        timeSlot: TimeSlot
    ): Result<Unit>

    suspend fun getReservationsForCurrentUser(): Result<List<Reservation>>

    suspend fun removeReservation(
        foodEstablishmentId: String,
        timeSlot: TimeSlot
    ): Result<Unit>
}