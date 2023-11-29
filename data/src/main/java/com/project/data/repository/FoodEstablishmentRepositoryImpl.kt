package com.project.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.StorageReference
import com.project.data.mapper.Mapper.toDomain
import com.project.data.mapper.Mapper.toDto
import com.project.data.model.FoodEstablishmentDto
import com.project.domain.model.Comment
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import com.project.domain.repository.SelectedDateForBookingLocalRepository
import com.project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

private const val FOOD_ESTABLISHMENT_COLLECTION = "food_establishments"

class FoodEstablishmentRepositoryImpl @Inject constructor(
    private val storage: StorageReference,
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository
) : FoodEstablishmentRepository {

    override suspend fun registerFoodEstablishment(foodEstablishment: FoodEstablishment): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val imagesUrls = mutableListOf<String>()
                foodEstablishment.photoList.forEach {
                    val ref = storage.child("${foodEstablishment.name}_${it.index}")
                    it.uri?.let { photoUri ->
                        ref.putFile(photoUri).await()
                    }
                    val url: String = ref.downloadUrl.await().toString()
                    imagesUrls.add(url)
                }
                firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
                    .add(foodEstablishment.toDto(imagesUrls)).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun fetchFoodEstablishments(
        city: String,
        tags: List<String>,
        withTimeFilters: Boolean
    ): Result<List<FoodEstablishment>> =
        withContext(Dispatchers.IO) {
            try {
                val collection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
                val query = if (city.isNotEmpty()) {
                    collection.whereEqualTo(FoodEstablishment::city.name, city)
                } else {
                    collection
                }
                val task = query.get().await()

                var foodEstablishment = task.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject<FoodEstablishmentDto>()?.toDomain()
                }
                if (withTimeFilters) {
                    val selectedDate = SelectedDateForBookingLocalRepository.getSavedDate()
                    val selectedTimeFrom =
                        SelectedDateForBookingLocalRepository.getSelectedTimeFrom()
                    val selectedTimeTo = SelectedDateForBookingLocalRepository.getSelectedTimeTo()
                    val peopleCount = SelectedDateForBookingLocalRepository.getPeopleCount()
                    val newList = filterFoodEstablishments(
                        selectedDate = selectedDate,
                        startTime = selectedTimeFrom,
                        endTime = selectedTimeTo,
                        numberOfTables = peopleCount / 4,
                        foodEstablishments = foodEstablishment
                    )
                    foodEstablishment = newList
                }
                Result.success(foodEstablishment)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private fun filterFoodEstablishments(
        selectedDate: Long,
        startTime: Long,
        endTime: Long,
        numberOfTables: Int,
        foodEstablishments: List<FoodEstablishment>
    ): List<FoodEstablishment> {
        val selectedDateCalendar = Calendar.getInstance()
        selectedDateCalendar.timeInMillis = selectedDate

        val startTimeCalendar = Calendar.getInstance()
        startTimeCalendar.timeInMillis = startTime

        val endTimeCalendar = Calendar.getInstance()
        endTimeCalendar.timeInMillis = endTime

        //TODO - fix logic to found not only 10:30-11:00 timeSlots, but also with more than standart interval
        return foodEstablishments.filter { establishment ->
            establishment.tablesForBooking.count { table ->
                table.timeSlots.any {
                    val timeSlotTimeFromCalendar = Calendar.getInstance()
                    timeSlotTimeFromCalendar.timeInMillis = it.timeFrom

                    val timeSlotTimeToCalendar = Calendar.getInstance()
                    timeSlotTimeToCalendar.timeInMillis = it.timeTo

                    selectedDateCalendar.get(Calendar.YEAR) == timeSlotTimeFromCalendar.get(Calendar.YEAR) &&
                            selectedDateCalendar.get(Calendar.MONTH) == timeSlotTimeFromCalendar.get(
                        Calendar.MONTH
                    ) &&
                            selectedDateCalendar.get(Calendar.DAY_OF_MONTH) == timeSlotTimeFromCalendar.get(
                        Calendar.DAY_OF_MONTH
                    ) &&
                            startTimeCalendar.get(Calendar.HOUR_OF_DAY) == timeSlotTimeFromCalendar.get(
                        Calendar.HOUR_OF_DAY
                    ) &&
                            startTimeCalendar.get(Calendar.MINUTE) == timeSlotTimeFromCalendar.get(
                        Calendar.MINUTE
                    ) &&
                            endTimeCalendar.get(Calendar.HOUR_OF_DAY) == timeSlotTimeToCalendar.get(
                        Calendar.HOUR_OF_DAY
                    ) &&
                            endTimeCalendar.get(Calendar.MINUTE) == timeSlotTimeToCalendar.get(
                        Calendar.MINUTE
                    ) &&
                            it.reservatorName == null &&
                            it.reservatorEmail == null &&
                            it.notes == null
                }
            } >= numberOfTables
        }
    }

    override suspend fun getFoodEstablishmentById(id: String): Result<FoodEstablishment> =
        withContext(Dispatchers.IO) {
            try {
                val collection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
                val task = collection.whereEqualTo(FoodEstablishment::id.name, id).get().await()
                val posts = task.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject<FoodEstablishmentDto>()?.toDomain()
                }
                Result.success(posts[0])
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun addComment(
        foodEstablishmentId: String,
        commentText: String,
        rating: Int
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val currentUser = userRepository.currentUser?.displayName
        try {
            val collection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
            val task =
                collection.whereEqualTo(FoodEstablishment::id.name, foodEstablishmentId).get()
                    .await()
            val idInDatabase = task.documents[0].id
            val foodEstablishmentRef = collection.document(idInDatabase)

            val document = foodEstablishmentRef.get().await()
            val foodEstablishment = document.toObject(FoodEstablishmentDto::class.java)?.toDomain()
            val currentComments = foodEstablishment?.comments?.toMutableList()

            val currentDate = Calendar.getInstance().timeInMillis
            val idOfComment = UUID.randomUUID().toString()
            val newComment = Comment(
                id = idOfComment,
                author = currentUser ?: "",
                commentText = commentText.ifEmpty { null },
                rating = rating,
                dateAdded = currentDate
            )
            currentComments?.add(newComment)

            val images: List<String> =
                foodEstablishment?.photoList?.map { it.uri.toString() } ?: emptyList()
            val updatedFoodEstablishment: FoodEstablishmentDto? =
                foodEstablishment?.copy(comments = currentComments ?: emptyList())?.toDto(images)

            if (updatedFoodEstablishment != null) {
                foodEstablishmentRef.set(updatedFoodEstablishment).await()
                return@withContext Result.success(Unit)
            }
            Result.failure(Exception("Comment was not added successfully"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFoodEstablishmentOfCurrentUser(): Result<List<FoodEstablishment>> =
        withContext(Dispatchers.IO) {
            try {
                val allFoodEstablishments = fetchFoodEstablishments().getOrElse {
                    return@withContext Result.failure(Throwable("Food establishment was not fetched successfully"))
                }
                val currentUser = userRepository.currentUser
                val finalList = allFoodEstablishments.filter {
                    it.ownerName == currentUser?.displayName
                }
                Result.success(finalList)
            } catch (t: Throwable) {
                Result.failure(t)
            }
        }

    override suspend fun addReplyForComment(
        foodEstablishmentId: String,
        commentId: String,
        replyText: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val collection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)
            val task =
                collection.whereEqualTo(FoodEstablishment::id.name, foodEstablishmentId).get()
                    .await()
            val idInDatabase = task.documents[0].id
            val foodEstablishmentRef = collection.document(idInDatabase)

            val document = foodEstablishmentRef.get().await()
            val foodEstablishment = document.toObject(FoodEstablishmentDto::class.java)?.toDomain()
            val currentComments = foodEstablishment?.comments?.toMutableList()

            var indexOfComment = 0
            foodEstablishment?.comments?.forEachIndexed { index, comment ->
                if (comment.id == commentId) {
                    indexOfComment = index
                }
            }
            val currentDate = Calendar.getInstance().timeInMillis
            val commentForChanging = foodEstablishment?.comments?.get(indexOfComment)?.copy(
                textOfReplyToComment = replyText,
                dateOfReply = currentDate
            )

            commentForChanging?.let {
                currentComments?.set(indexOfComment, commentForChanging)
            }

            val images: List<String> =
                foodEstablishment?.photoList?.map { it.uri.toString() } ?: emptyList()
            val updatedFoodEstablishment: FoodEstablishmentDto? =
                foodEstablishment?.copy(comments = currentComments ?: emptyList())?.toDto(images)

            if (updatedFoodEstablishment != null) {
                foodEstablishmentRef.set(updatedFoodEstablishment).await()
                return@withContext Result.success(Unit)
            }
            Result.failure(Exception("Reply \"$replyText\" was not added successfully"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}