package com.project.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.presentation.ui.navigation.AppDestinations
import com.project.presentation.ui.navigation.SetupNavGraph
import com.project.presentation.ui.screens.bottom_navigation.BottomNavigation
import com.project.presentation.ui.theme.RestoBookingTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidApp() {

    val navController = rememberNavController()
    RestoBookingTheme {
        val showNavigationBar =
            navController.currentBackStackEntryAsState().value?.destination?.route?.let { route ->
                route != AppDestinations.Splash.route && route != AppDestinations.Login.route && route != AppDestinations.SignUp.route
            } ?: false

        Surface {
            Scaffold(
                bottomBar = {
                    if (showNavigationBar) {
                        BottomNavigation(navController = navController)
                    }
                },
                topBar = {
                    if (showNavigationBar) {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = stringResource(R.string.resto_booking),
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                                )
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black)
                        )
                    }
                }
            ) {
                SetupNavGraph(navController = navController)
            }
        }
    }
}