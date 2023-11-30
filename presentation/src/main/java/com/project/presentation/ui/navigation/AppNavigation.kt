package com.project.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.presentation.R
import com.project.presentation.ui.navigation.ArgsName.CITY_ARG
import com.project.presentation.ui.navigation.ArgsName.FE_DETAILS_FOR_ADMIN_ID_ARG
import com.project.presentation.ui.navigation.ArgsName.ID_ARG
import com.project.presentation.ui.navigation.ArgsName.TAGS_ARG
import com.project.presentation.ui.screens.add_food_establishments.AddFoodEstablishmentsScreen
import com.project.presentation.ui.screens.bottom_navigation.BottomNavigationViewModel
import com.project.presentation.ui.screens.fedetailsforadmin.FoodEstablishmentDetailsForAdminScreen
import com.project.presentation.ui.screens.home.HomeScreen
import com.project.presentation.ui.screens.login.LoginScreen
import com.project.presentation.ui.screens.myfoodestablishment.MyFoodEstablishmentsScreen
import com.project.presentation.ui.screens.myreservations.MyReservationsScreen
import com.project.presentation.ui.screens.pdp.PdpScreen
import com.project.presentation.ui.screens.profile.ProfileScreen
import com.project.presentation.ui.screens.setreservation.SetReservationScreen
import com.project.presentation.ui.screens.signup.SignUpScreen
import com.project.presentation.ui.screens.spalsh.SplashScreen
import com.project.presentation.ui.screens.srp.SrpScreen

object ArgsName {
    const val CITY_ARG = "city"
    const val TAGS_ARG = "tags"
    const val ID_ARG = "id"
    const val FE_DETAILS_FOR_ADMIN_ID_ARG = "id_fe_for_admin"
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
        AppDestinations("reservations_screen", "Мої бронювання", R.drawable.ic_my_reservation)

    object Profile : AppDestinations("profile_screen", "Профіль", R.drawable.ic_profile)
    object AddFoodEstablishments : AppDestinations("add_food_establishments")
    object Srp : AppDestinations("srp_screen")
    object Pdp : AppDestinations("pdp_screen")
    object AddReservation : AppDestinations("add_reservation_screen")
    object MyFoodEstablishmentsScreen : AppDestinations("my_food_establishments_screen")
    object FoodEstablishmentDetailsForAdminScreen :
        AppDestinations("food_establishments_for_admin_screen")
}

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomNavigationViewModel: BottomNavigationViewModel
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
                bottomNavigationViewModel = bottomNavigationViewModel,
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
            MyReservationsScreen(
                navigateToPdp = { id ->
                    navController.navigate("${AppDestinations.Pdp.route}/$id")
                }
            )
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
                bottomNavigationViewModel = bottomNavigationViewModel,
                navigateToAddFoodEstablishment = {
                    navController.navigate(AppDestinations.AddFoodEstablishments.route)
                },
                navigateToLoginScreen = {
                    navController.navigate(AppDestinations.Login.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
                navigateToMyFoodEstablishmentsScreen = {
                    navController.navigate(AppDestinations.MyFoodEstablishmentsScreen.route)
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
                    navController.popBackStack()
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
                },
                navigateToReservation = { id ->
                    navController.navigate("${AppDestinations.AddReservation.route}/$id")
                }
            )
        }
        composable(
            route = "${AppDestinations.AddReservation.route}/{${ID_ARG}}",
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
            SetReservationScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = AppDestinations.MyFoodEstablishmentsScreen.route,
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
            MyFoodEstablishmentsScreen(
                navigateToFoodEstablishmentsDetailsForAdmin = {
                    navController.navigate("${AppDestinations.FoodEstablishmentDetailsForAdminScreen.route}/$it")
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${AppDestinations.FoodEstablishmentDetailsForAdminScreen.route}/{$FE_DETAILS_FOR_ADMIN_ID_ARG}",
            arguments = listOf(
                navArgument(FE_DETAILS_FOR_ADMIN_ID_ARG) { type = NavType.StringType }),
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
            FoodEstablishmentDetailsForAdminScreen(
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
