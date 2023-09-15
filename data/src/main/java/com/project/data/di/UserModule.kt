package com.project.data.di

import com.google.firebase.auth.FirebaseAuth
import com.project.data.interactors.LoginUserUseCaseImpl
import com.project.data.interactors.SignUpUserUseCaseImpl
import com.project.data.repository.UserRepositoryImpl
import com.project.domain.repository.UserRepository
import com.project.domain.usecase.LoginUserUseCase
import com.project.domain.usecase.SignUpUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UserModule {

    @Provides
    fun provideUserRepository(auth: FirebaseAuth): UserRepository = UserRepositoryImpl(auth)

    @Provides
    fun provideSignUpUserUseCase(
        repository: UserRepository
    ): SignUpUserUseCase = SignUpUserUseCaseImpl(repository)

    @Provides
    fun provideLoginUserUseCase(
        repository: UserRepository
    ): LoginUserUseCase = LoginUserUseCaseImpl(repository)
}