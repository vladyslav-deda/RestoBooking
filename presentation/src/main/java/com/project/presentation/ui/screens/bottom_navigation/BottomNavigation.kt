package com.project.presentation.ui.screens.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.presentation.R
import com.project.presentation.ui.navigation.AppDestinations

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BottomNavigationViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getCurrentScreen(navController.currentBackStackEntryAsState().value?.destination?.route)
        ?.let { screen ->
            Row(
                modifier = modifier
                    .background(Color.Black)
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                viewModel.getListOfItems().forEach { item ->
                    val badgeCount = if (item.route == AppDestinations.Profile.route) {
                        uiState.unrepliedComments
                    } else 0
                    BottomNavigationItem(
                        item = item,
                        isSelected = item.route == screen.route,
                        badgeCount = badgeCount ?: 0
                    ) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    item: AppDestinations,
    isSelected: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    val background = if (isSelected) colorResource(id = R.color.main_yellow) else Color.Transparent
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .padding(horizontal = 15.dp)
            .clickable(onClick = {
                onClick()
            })
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item.icon?.let {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }
        if (badgeCount > 0) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                containerColor = colorResource(id = R.color.red),
                contentColor = Color.White
            ) {
                Text(text = badgeCount.toString())
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 16777215
)
@Composable
fun PreviewBottomNavigation() {
//    BottomNavigation(
//        navController = rememberNavController(),
//        viewModel = BottomNavigationViewModel()
//    )
}