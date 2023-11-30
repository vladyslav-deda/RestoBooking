package com.project.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.presentation.ui.navigation.AppDestinations
import com.project.presentation.ui.navigation.SetupNavGraph
import com.project.presentation.ui.screens.bottom_navigation.BottomNavigation
import com.project.presentation.ui.screens.bottom_navigation.BottomNavigationViewModel
import com.project.presentation.ui.theme.RestoBookingTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidApp() {

    val navController = rememberNavController()
    val bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()

    RestoBookingTheme {
        val showNavigationBar =
            navController.currentBackStackEntryAsState().value?.destination?.route?.let { route ->
                route == AppDestinations.Home.route || route == AppDestinations.Reservations.route || route == AppDestinations.Profile.route
            } ?: false

        Surface {
            Scaffold(
                bottomBar = {
                    if (showNavigationBar) {
                        BottomNavigation(
                            navController = navController,
                            viewModel = bottomNavigationViewModel
                        )
                    }
                }
            ) {
                SetupNavGraph(
                    navController = navController,
                    bottomNavigationViewModel = bottomNavigationViewModel
                )
            }
        }
    }
}