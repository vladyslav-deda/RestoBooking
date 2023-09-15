package com.project.data.interactors

import com.project.domain.repository.UserRepository
import com.project.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUserUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : LoginUserUseCase {

    override suspend fun invoke(email: String, password: String): Result<Unit> = withContext(Dispatchers.IO) {
        repository.firebaseSignInWithEmailAndPassword(email, password)
    }
}