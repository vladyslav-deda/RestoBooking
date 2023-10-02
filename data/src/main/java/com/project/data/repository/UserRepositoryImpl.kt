package com.project.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.domain.model.CreateUser
import com.project.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
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


    override suspend fun firebaseSignUpNewUser(user: CreateUser): Result<Unit> =
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

    override fun isUserLoggedIn() = currentUser != null
}