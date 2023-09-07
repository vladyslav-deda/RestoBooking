package com.project.presentation.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import com.google.accompanist.navigation.animation.AnimatedNavHost
//import com.google.accompanist.navigation.animation.composable
import com.project.presentation.ui.screens.login.LoginScreen
import com.project.presentation.ui.screens.spalsh.SplashScreen

sealed class AppDestinations(val route: String) {
    object Splash : AppDestinations("splash_screen")
    object Login : AppDestinations("login_screen")
    object SignUp : AppDestinations("sign_up_screen")
    object Home : AppDestinations("home_screen")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Splash.route,
        modifier = modifier,
//        exitTransition = {
//            fadeOut()
//        },
//        enterTransition = {
//            fadeIn()
//        }
    ) {
        composable(
            route = AppDestinations.Splash.route,
        ) {
            SplashScreen(
                navigateLogin = {
                    navController.navigate(AppDestinations.Login.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppDestinations.Login.route) {
            LoginScreen(
                homeNavigate = {
                    navController.navigate(AppDestinations.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
                signUpNavigate = {
                    navController.navigate(AppDestinations.SignUp.route)
                }
            )
        }

        composable(route = AppDestinations.Home.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Home Screen", style = MaterialTheme.typography.bodyLarge)
            }
        }
        composable(route = AppDestinations.SignUp.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "SignUp Screen", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
