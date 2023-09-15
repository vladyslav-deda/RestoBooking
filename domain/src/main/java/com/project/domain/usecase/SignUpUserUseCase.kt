package com.project.domain.usecase

import com.project.domain.model.User

interface SignUpUserUseCase {

    suspend operator fun invoke(user: User): Result<Unit>
}