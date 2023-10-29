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
import com.project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar
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
                    it.uri?.let { it1 -> ref.putFile(it1).await() }
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
        tags: List<String>
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

                val posts = task.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject<FoodEstablishmentDto>()?.toDomain()
                }
                Result.success(posts)
            } catch (e: Exception) {
                Result.failure(e)
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
            val foodEstablishment = document.toObject(FoodEstablishment::class.java)

            // Отримати поточний список коментарів
            val currentComments = foodEstablishment?.comments?.toMutableList()

            val currentDate = Calendar.getInstance().timeInMillis
            // Додати новий коментар
            val newComment = Comment(
                author = currentUser ?: "",
                commentText = commentText,
                rating = rating,
                dateAdded = currentDate
            )
            currentComments?.add(newComment)

            // Оновити коментари у копії об'єкта FoodEstablishment
            val updatedFoodEstablishment =
                foodEstablishment?.copy(comments = currentComments ?: emptyList())

            updatedFoodEstablishment?.let {
                foodEstablishmentRef.set(it).await()

                Result.success(Unit)
            }
            Result.failure(Exception("Comment was not added successfully"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}