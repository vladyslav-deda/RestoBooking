package com.project.domain.usecase

import com.project.domain.model.User

interface GetCurrentUserUseCase {

    operator fun invoke(): Result<User>
}