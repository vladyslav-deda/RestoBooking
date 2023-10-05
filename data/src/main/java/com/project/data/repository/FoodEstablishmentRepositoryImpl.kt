package com.project.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.StorageReference
import com.project.data.mapper.Mapper.toDomain
import com.project.data.mapper.Mapper.toDto
import com.project.data.model.FoodEstablishmentDto
import com.project.domain.model.FoodEstablishment
import com.project.domain.repository.FoodEstablishmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val FOOD_ESTABLISHMENT_COLLECTION = "food_establishments"

class FoodEstablishmentRepositoryImpl @Inject constructor(
    private val storage: StorageReference,
    private val firestore: FirebaseFirestore
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

    override suspend fun fetchFoodEstablishments(): Result<List<FoodEstablishment>> =
        withContext(Dispatchers.IO) {
            try {
                val postCollection = firestore.collection(FOOD_ESTABLISHMENT_COLLECTION)

                val task = postCollection.get().await()
                val posts = task.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject<FoodEstablishmentDto>()?.toDomain()
                }
                Result.success(posts)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}