package com.project.domain.usecase

import com.project.domain.model.CreateUser

interface SignUpUserUseCase {

    suspend operator fun invoke(user: CreateUser): Result<Unit>
}