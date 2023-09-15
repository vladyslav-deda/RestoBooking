package com.project.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.domain.model.User
import com.project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {

    override val currentUser get() = auth.currentUser

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Unit> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }


    override suspend fun firebaseSignUpNewUser(user: User): Result<Unit> =
        try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(user.nameSurname)
                .build()
            result.user?.updateProfile(profileUpdates)?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }


    override fun logout() = auth.signOut()
}