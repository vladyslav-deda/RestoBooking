package com.project.presentation.ui.screens.bottom_navigation

import androidx.lifecycle.ViewModel
import com.project.presentation.ui.navigation.AppDestinations

class BottomNavigationViewModel : ViewModel() {

    fun getCurrentScreen(route: String?): AppDestinations? = when (route) {
        AppDestinations.Home.route -> AppDestinations.Home
        AppDestinations.Reservations.route -> AppDestinations.Reservations
        AppDestinations.Profile.route -> AppDestinations.Profile
        else -> null
    }

    fun getListOfItems() =
        setOf(AppDestinations.Home, AppDestinations.Reservations, AppDestinations.Profile)

}