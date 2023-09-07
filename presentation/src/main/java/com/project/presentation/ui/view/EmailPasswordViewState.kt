package com.project.presentation.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.project.presentation.R

data class EmailPasswordViewState(
    val email: String = "",
    val password: PasswordViewState = PasswordViewState(),
    val errorMessage: String? = null
)  {
    @StringRes
    val emailLabelText = R.string.email

    fun isButtonEnabled(): Boolean =
        errorMessage == null && email.isNotEmpty() && password.isEmpty().not()
}

data class PasswordViewState(
    val password: String = "",
    val showPassword: Boolean = false,
    val isError: Boolean = false,
    @StringRes
    val labelText: Int = R.string.password
) {

    @DrawableRes
    fun endIcon(): Int {
        return if (showPassword) R.drawable.ic_visible else R.drawable.ic_invisible
    }

    fun isEmpty(): Boolean = password.isEmpty()
}