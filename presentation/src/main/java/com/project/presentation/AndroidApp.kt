package com.project.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.presentation.ui.navigation.SetupNavGraph
import com.project.presentation.ui.theme.RestoBookingTheme

@Composable
fun AndroidApp() {

    val navController = rememberNavController()
    Log.i("myLogs", "AndroidApp: ${navController.currentBackStackEntryAsState().value?.destination?.route}")
    RestoBookingTheme {
        SetupNavGraph(navController = navController)
    }
}