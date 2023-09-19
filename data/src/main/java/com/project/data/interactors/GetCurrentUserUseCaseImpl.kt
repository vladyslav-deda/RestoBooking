package com.project.data.interactors

import com.project.domain.model.User
import com.project.domain.repository.UserRepository
import com.project.domain.usecase.GetCurrentUserUseCase
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : GetCurrentUserUseCase {

    override fun invoke(): Result<User> = runCatching {
        val currentUser = repository.currentUser
        User(
            nameSurname = currentUser?.displayName ?: "",
            email = currentUser?.email ?: ""
        )
    }
}