package com.project.domain.usecase

interface LoginUserUseCase {

    suspend operator fun invoke(email: String, password: String): Result<Unit>
}
