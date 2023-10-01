package com.project.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.project.presentation.R
import com.project.presentation.ui.screens.add_food_establishments.AddFoodEstablishmentsScreen
import com.project.presentation.ui.screens.home.HomeScreen
import com.project.presentation.ui.screens.login.LoginScreen
import com.project.presentation.ui.screens.profile.ProfileScreen
import com.project.presentation.ui.screens.signup.SignUpScreen
import com.project.presentation.ui.screens.spalsh.SplashScreen

sealed class AppDestinations(
    val route: String,
    val title: String? = null,
    @DrawableRes
    val icon: Int? = null
) {
    object Splash : AppDestinations("splash_screen")
    object Login : AppDestinations("login_screen")
    object SignUp : AppDestinations("sign_up_screen")
    object Home : AppDestinations("home_screen", "Home", R.drawable.ic_home)
    object Reservations :
        AppDestinations("reservations_screen", "Reservations", R.drawable.ic_my_reservation)

    object Profile : AppDestinations("profile_screen", "Profile", R.drawable.ic_profile)
    object AddFoodEstablishments : AppDestinations("add_food_establishments")
}

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Splash.route,
        modifier = modifier,
    ) {
        composable(
            route = AppDestinations.Splash.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }
        ) {
            SplashScreen(
                navigateLogin = {
                    navController.navigate(AppDestinations.Home.route) {
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

        composable(route = AppDestinations.SignUp.route) {
            SignUpScreen(
                homeNavigate = {
                    navController.navigate(AppDestinations.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(route = AppDestinations.Home.route) {
            HomeScreen()
        }
        composable(route = AppDestinations.Reservations.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Reservations Screen", style = MaterialTheme.typography.bodyLarge)
            }
        }
        composable(route = AppDestinations.Profile.route) {
            ProfileScreen(
                navigateToAddFoodEstablishments = {
                    navController.navigate(AppDestinations.AddFoodEstablishments.route)
                }
            )
        }
        composable(route = AppDestinations.AddFoodEstablishments.route){
            AddFoodEstablishmentsScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
