package com.project.data.interactors

import com.project.domain.model.CreateUser
import com.project.domain.repository.UserRepository
import com.project.domain.usecase.SignUpUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUserUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : SignUpUserUseCase {

    override suspend fun invoke(user: CreateUser): Result<Unit> = withContext(Dispatchers.IO) {
        repository.firebaseSignUpNewUser(user = user)
    }
}