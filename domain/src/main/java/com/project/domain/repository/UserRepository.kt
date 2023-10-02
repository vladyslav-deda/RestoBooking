package com.project.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.project.domain.model.CreateUser

interface UserRepository {

    val currentUser: FirebaseUser?

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Result<Unit>

    suspend fun firebaseSignUpNewUser(user: CreateUser): Result<Unit>

    fun logout()

    fun isUserLoggedIn(): Boolean
}