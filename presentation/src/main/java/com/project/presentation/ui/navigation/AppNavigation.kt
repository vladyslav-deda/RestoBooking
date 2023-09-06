package com.project.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.presentation.ui.screens.SplashScreen

sealed class AppDestinations(val route: String) {
    object Splash : AppDestinations("splash_screen")
    object Login : AppDestinations("login_screen")
    object SignIn : AppDestinations("sign_in_screen")
    object Home : AppDestinations("home_screen")
}

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Splash.route,
        modifier = modifier
    ) {
        composable(route = AppDestinations.Splash.route) {
            SplashScreen(
                navigateLogin = {
                    navController.navigate(AppDestinations.Login.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
