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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.presentation.R
import com.project.presentation.ui.navigation.PdpDestinationArgs.ID_ARG
import com.project.presentation.ui.navigation.ReservationArgs.SELECTED_DATE_ARG
import com.project.presentation.ui.navigation.SrpDestinationsArgs.CITY_ARG
import com.project.presentation.ui.navigation.SrpDestinationsArgs.TAGS_ARG
import com.project.presentation.ui.screens.add_food_establishments.AddFoodEstablishmentsScreen
import com.project.presentation.ui.screens.home.HomeScreen
import com.project.presentation.ui.screens.login.LoginScreen
import com.project.presentation.ui.screens.pdp.PdpScreen
import com.project.presentation.ui.screens.profile.ProfileScreen
import com.project.presentation.ui.screens.reservation.ReservationScreen
import com.project.presentation.ui.screens.signup.SignUpScreen
import com.project.presentation.ui.screens.spalsh.SplashScreen
import com.project.presentation.ui.screens.srp.SrpScreen

object SrpDestinationsArgs {
    const val CITY_ARG = "city"
    const val TAGS_ARG = "tags"
}

object PdpDestinationArgs {
    const val ID_ARG = "name"
}

object ReservationArgs {
    const val SELECTED_DATE_ARG = "date"
}

sealed class AppDestinations(
    val route: String,
    val title: String? = null,
    @DrawableRes
    val icon: Int? = null
) {
    object Splash : AppDestinations("splash_screen")
    object Login : AppDestinations("login_screen")
    object SignUp : AppDestinations("sign_up_screen")
    object Home : AppDestinations("home_screen", "Головна", R.drawable.ic_home)
    object Reservations :
        AppDestinations("reservations_screen", "Мої заклади", R.drawable.ic_my_reservation)

    object Profile : AppDestinations("profile_screen", "Профіль", R.drawable.ic_profile)
    object AddFoodEstablishments : AppDestinations("add_food_establishments")
    object Srp : AppDestinations("srp_screen")
    object Pdp : AppDestinations("pdp_screen")
    object AddReservation: AppDestinations("add_reservation_screen")
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
                    navController.navigate(AppDestinations.Login.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
                navigateHome = {
                    navController.navigate(AppDestinations.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = AppDestinations.Login.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
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

        composable(
            route = AppDestinations.SignUp.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            SignUpScreen(
                homeNavigate = {
                    navController.navigate(AppDestinations.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = AppDestinations.Home.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            HomeScreen(
                navigateToSrp = { city, tags ->
                    navController.navigate("${AppDestinations.Srp.route}/$city/$tags")
                }
            )
        }
        composable(
            route = AppDestinations.Reservations.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Reservations Screen", style = MaterialTheme.typography.bodyLarge)
            }
        }
        composable(
            route = AppDestinations.Profile.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            ProfileScreen(
                navigateToAddFoodEstablishment = {
                    navController.navigate(AppDestinations.AddFoodEstablishments.route)
                },
                navigateToLoginScreen = {
                    navController.navigate(AppDestinations.Login.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = AppDestinations.AddFoodEstablishments.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            AddFoodEstablishmentsScreen(
                navigateBack = {
                    navController.navigate(AppDestinations.Profile.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
                navigateToProfileScreen = {
                    navigateAndClearBackStack(AppDestinations.Profile.route, navController)
                }
            )
        }
        composable(
            route = "${AppDestinations.Srp.route}/{$CITY_ARG}/{$TAGS_ARG}",
            arguments = listOf(
                navArgument(CITY_ARG) { type = NavType.StringType },
                navArgument(TAGS_ARG) { type = NavType.StringArrayType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            SrpScreen(
                navigateBack = {
                    navController.navigate(AppDestinations.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
                navigateToPdp = { id ->
                    navController.navigate("${AppDestinations.Pdp.route}/$id")
                }
            )
        }
        composable(
            route = "${AppDestinations.Pdp.route}/{$ID_ARG}",
            arguments = listOf(
                navArgument(ID_ARG) { type = NavType.StringType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            PdpScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${AppDestinations.AddReservation.route}/{$SELECTED_DATE_ARG}",
            arguments = listOf(
                navArgument(SELECTED_DATE_ARG) { type = NavType.LongType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700)
                )
            }) {
            ReservationScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}

fun navigateAndClearBackStack(route: String, navController: NavHostController) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }
}
