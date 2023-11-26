package com.project.presentation.ui.screens.myreservations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.domain.model.Photo
import com.project.presentation.R
import com.project.presentation.ui.screens.myreservations.view.MyReservationItemView
import com.project.presentation.ui.view.SrpItemView
import com.project.presentation.ui.view.SrpItemViewState
import com.project.presentation.ui.view.common.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReservationsScreen(
    modifier: Modifier = Modifier,
    viewModel: MyReservationsScreenViewModel = hiltViewModel(),
    navigateToPdp:(String)->Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
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
    ) { contentPadding ->
        Box(
            modifier = modifier
                .padding(contentPadding)
                .fillMaxSize(),
        ) {
            if (uiState.isLoading) {
                LoadingView()
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (uiState.reservations.isEmpty()) {
                        EmptyListView()
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            items(uiState.reservations) {  item ->
                                MyReservationItemView(
                                    viewState = item,
                                ) {
                                    navigateToPdp(item.foodEstablishmentId)
                                }

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyListView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Жодного бронювання не було знайдено",
            style = MaterialTheme.typography.titleMedium
        )

    }
}