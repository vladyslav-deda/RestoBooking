package com.project.presentation.ui.screens.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.screens.bottom_navigation.BottomNavigationViewModel
import com.project.presentation.ui.view.common.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel,
    navigateToAddFoodEstablishment: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    navigateToMyFoodEstablishmentsScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SideEffect {
        viewModel.retrieveDetailsInfo()
        bottomNavigationViewModel.updateBadgeCounter()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Мій профіль",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black)
            )
        }
    ) { contentPadding ->
        if (uiState.isLoading) {
            LoadingView()
        } else {
            Column(
                modifier = modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                TitleView(
                    nameSurname = uiState.currentUser?.nameSurname ?: "",
                    email = uiState.currentUser?.email ?: ""
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 40.dp)
                ) {
                    MenuItem(
                        icon = R.drawable.ic_building,
                        title = stringResource(R.string.my_food_establishments),
                        unrepliedCommentsCount = uiState.unrepliedCommentsCount
                    ) {
                        navigateToMyFoodEstablishmentsScreen()
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    MenuItem(
                        icon = R.drawable.ic_add,
                        title = stringResource(R.string.add_a_food_establishment)
                    ) {
                        navigateToAddFoodEstablishment()
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    MenuItem(
                        icon = R.drawable.ic_user,
                        title = stringResource(R.string.personal_details)
                    ) {

                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    MenuItem(
                        icon = R.drawable.ic_logout,
                        title = stringResource(R.string.logout)
                    ) {
                        viewModel.logout()
                        navigateToLoginScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun TitleView(
    modifier: Modifier = Modifier,
    nameSurname: String,
    email: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.main_yellow)),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
        ) {
            Text(
                text = nameSurname,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.W700
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.titleSmall.copy(color = colorResource(id = R.color.dark_gray))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuItem(
    modifier: Modifier = Modifier,
    @DrawableRes
    icon: Int,
    title: String,
    unrepliedCommentsCount: Int? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                painterResource(id = icon),
                contentDescription = "",
                tint = colorResource(id = R.color.main_yellow)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.W400
                )
            )
        }
        if (unrepliedCommentsCount != null && unrepliedCommentsCount > 0) {
            Badge(
                containerColor = colorResource(id = R.color.red),
                contentColor = Color.White
            ) {
                Text(text = unrepliedCommentsCount.toString())
            }
        }
    }
}