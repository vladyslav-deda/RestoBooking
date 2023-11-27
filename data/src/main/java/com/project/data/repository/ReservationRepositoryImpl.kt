package com.project.data.repository

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.StorageReference
import com.project.data.mapper.Mapper.toDomain
import com.project.data.mapper.Mapper.toDto
import com.project.data.model.FoodEstablishmentDto
import com.project.domain.model.FoodEstablishment
import com.project.domain.model.Reservation
import com.project.domain.model.Table
import com.project.domain.model.TimeSlot
import com.project.domain.repository.ReservationRepository
import com.project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

private const val FOOD_ESTABLISHMENT_COLLECTION = "food_establishments"

class ReservationRepositoryImpl @Inject constructor(
    private val storage: StorageReference,
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository
) : ReservationRepository {

    override suspend fun addReservation(
        foodEstablishmentId: String,
        timeSlot: TimeSlot
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val currentUser = userRepository.currentUser
        try {
            val collection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
            val task =
                collection.whereEqualTo(FoodEstablishment::id.name, foodEstablishmentId).get()
                    .await()
            val idInDatabase = task.documents[0].id
            val foodEstablishmentRef = collection.document(idInDatabase)

            val document = foodEstablishmentRef.get().await()
            val foodEstablishment = document.toObject(FoodEstablishmentDto::class.java)?.toDomain()
            val tablesForBooking = foodEstablishment?.tablesForBooking?.toMutableList()

            val tableAndTimeSlotIndexes = getTableAndTimeSlotIndexes(
                tablesForBooking = tablesForBooking,
                timeSlot = timeSlot,
                reservatorEmail = null,
                reservatorName = null
            )
            val newTimeSlot = timeSlot.copy(
                reservatorName = currentUser?.displayName,
                reservatorEmail = currentUser?.email
            )
            val tableForReservation =
                tablesForBooking?.get(tableAndTimeSlotIndexes.first!!)
            val newTimeSlots = tableForReservation?.timeSlots?.toMutableList()

            newTimeSlots?.set(tableAndTimeSlotIndexes.second!!, newTimeSlot)
            val newTable = tableForReservation?.copy(
                timeSlots = newTimeSlots ?: emptyList()
            )
            tablesForBooking?.set(tableAndTimeSlotIndexes.first!!, newTable!!)

            val images: List<String> =
                foodEstablishment?.photoList?.map { it.uri.toString() } ?: emptyList()
            val updatedFoodEstablishment: FoodEstablishmentDto? =
                foodEstablishment?.copy(tablesForBooking = tablesForBooking ?: emptyList())
                    ?.toDto(images)

            if (updatedFoodEstablishment != null) {
                foodEstablishmentRef.set(updatedFoodEstablishment).await()
                return@withContext Result.success(Unit)
            }
            Result.failure(Exception("Comment was not added successfully"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getReservationsForCurrentUser(): Result<List<Reservation>> =
        withContext(Dispatchers.IO) {
            try {
                val currentUserEmail = userRepository.currentUser?.email
                val collection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
                val task = collection.get().await()

                val foodEstablishments = task.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject<FoodEstablishmentDto>()?.toDomain()
                }

                val reservations = mutableListOf<Reservation>()

                val currentDate = Calendar.getInstance().time
                foodEstablishments.forEach { foodEstablishment ->
                    foodEstablishment.tablesForBooking.forEach {
                        it.timeSlots.forEach { timeSlot ->
                            val timeSlotDate = Date(timeSlot.timeFrom)
                            if (timeSlot.reservatorEmail == currentUserEmail/* && !timeSlotDate.before(currentDate)*/) {
                                reservations.add(
                                    Reservation(
                                        timeSlot = timeSlot,
                                        foodEstablishment = foodEstablishment
                                    )
                                )
                            }
                        }
                    }
                }

                Result.success(reservations)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun removeReservation(
        foodEstablishmentId: String,
        timeSlot: TimeSlot
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val currentUser = userRepository.currentUser
        try {
            val collection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
            val task =
                collection.whereEqualTo(FoodEstablishment::id.name, foodEstablishmentId).get()
                    .await()
            val idInDatabase = task.documents[0].id
            val foodEstablishmentRef = collection.document(idInDatabase)

            val document = foodEstablishmentRef.get().await()
            val foodEstablishment = document.toObject(FoodEstablishmentDto::class.java)?.toDomain()
            val tablesForBooking = foodEstablishment?.tablesForBooking?.toMutableList()

            val tableAndTimeSlotIndexes = getTableAndTimeSlotIndexes(
                tablesForBooking = tablesForBooking,
                timeSlot = timeSlot,
                reservatorEmail = currentUser?.email,
                reservatorName = currentUser?.displayName
            )
            val newTimeSlot = timeSlot.copy(
                reservatorName = null,
                reservatorEmail = null,
                notes = null
            )
            val tableForReservation =
                tablesForBooking?.get(tableAndTimeSlotIndexes.first!!)
            val newTimeSlots = tableForReservation?.timeSlots?.toMutableList()

            newTimeSlots?.set(tableAndTimeSlotIndexes.second!!, newTimeSlot)
            val newTable = tableForReservation?.copy(
                timeSlots = newTimeSlots ?: emptyList()
            )
            tablesForBooking?.set(tableAndTimeSlotIndexes.first!!, newTable!!)

            val images: List<String> =
                foodEstablishment?.photoList?.map { it.uri.toString() } ?: emptyList()
            val updatedFoodEstablishment: FoodEstablishmentDto? =
                foodEstablishment?.copy(tablesForBooking = tablesForBooking ?: emptyList())
                    ?.toDto(images)

            if (updatedFoodEstablishment != null) {
                foodEstablishmentRef.set(updatedFoodEstablishment).await()
                return@withContext Result.success(Unit)
            }
            Result.failure(Exception("Comment was not removed successfully"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getTableAndTimeSlotIndexes(
        tablesForBooking: MutableList<Table>?,
        timeSlot: TimeSlot,
        reservatorEmail: String?,
        reservatorName: String?
    ): Pair<Int?, Int?> {
        val timeFromCalendar = Calendar.getInstance().apply {
            timeInMillis = timeSlot.timeFrom
        }
        tablesForBooking?.forEachIndexed { tableIndex, table ->
            table.timeSlots.forEachIndexed { timeSlotIndex, slot ->
                val currentSlotTimeFromCalendar = Calendar.getInstance().apply {
                    timeInMillis = slot.timeFrom
                }
                if (
                    timeFromCalendar.get(Calendar.YEAR) == currentSlotTimeFromCalendar.get(Calendar.YEAR) &&
                    timeFromCalendar.get(Calendar.MONTH) == currentSlotTimeFromCalendar.get(Calendar.MONTH) &&
                    timeFromCalendar.get(Calendar.DAY_OF_MONTH) == currentSlotTimeFromCalendar.get(
                        Calendar.DAY_OF_MONTH
                    ) &&
                    timeFromCalendar.get(Calendar.HOUR_OF_DAY) == currentSlotTimeFromCalendar.get(
                        Calendar.HOUR_OF_DAY
                    ) &&
                    timeFromCalendar.get(Calendar.MINUTE) == currentSlotTimeFromCalendar.get(
                        Calendar.MINUTE
                    ) &&
                    timeSlot.reservatorEmail == reservatorEmail &&
                    timeSlot.reservatorName == reservatorName
                ) {
                    return Pair(tableIndex, timeSlotIndex)
                }
            }
        }
        return Pair(null, null)
    }
}