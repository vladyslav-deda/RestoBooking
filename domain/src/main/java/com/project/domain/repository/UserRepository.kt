package com.project.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.project.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val currentUser: FirebaseUser?

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Result<Unit>

    suspend fun firebaseSignUpNewUser(user: User): Result<Unit>

    fun logout()
}