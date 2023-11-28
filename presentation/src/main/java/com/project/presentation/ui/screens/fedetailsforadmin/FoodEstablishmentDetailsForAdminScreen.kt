package com.project.presentation.ui.screens.fedetailsforadmin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.project.presentation.R
import com.project.presentation.ui.screens.fedetailsforadmin.view.TimeSlotDetailsItemView
import com.project.presentation.ui.screens.fedetailsforadmin.view.TimeSlotDetailsViewState
import com.project.presentation.ui.screens.myreservations.view.MyReservationItemView
import com.project.presentation.ui.view.RatingBar
import com.project.presentation.ui.view.common.EmptyListView
import com.project.presentation.ui.view.common.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodEstablishmentDetailsForAdminScreen(
    modifier: Modifier = Modifier,
    viewModel: FoodEstablishmentDetailsForAdminViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val rotation = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(uiState.isTimeSlotsShowed) {
        rotation.animateTo(
            targetValue = if (uiState.isTimeSlotsShowed) 180f else 0f,
            animationSpec = tween(durationMillis = 800),
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
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
                    if (uiState.foodEstablishment == null) {
                        EmptyListView(text = "Щось пішло не так, спробуйте знову")
                    } else {
                        Column {
                            Column(
                                modifier = modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                            ) {
                                Text(
                                    text = uiState.foodEstablishment!!.name,
                                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
                                )
                                if (uiState.foodEstablishment!!.rating > 0) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    RatingBar(
                                        rating = uiState.foodEstablishment!!.rating
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(15.dp),
                                        painter = painterResource(id = R.drawable.ic_place),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.main_yellow)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "${uiState.foodEstablishment!!.city}, ${uiState.foodEstablishment!!.address}",
                                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height = 2.dp)
                                    .background(colorResource(id = R.color.main_yellow))
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(colorResource(id = R.color.light_yellow))
                                    .clickable {
                                        viewModel.handleTimeSlotsVisibility(!uiState.isTimeSlotsShowed)
                                    }
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Заброньовані столики",
                                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                                )
                                Icon(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .rotate(rotation.value),
                                    imageVector = Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.main_yellow)
                                )
                            }
                            AnimatedVisibility(visible = uiState.isTimeSlotsShowed) {
                                if (viewModel.getReservedTimeSlots().isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier.padding(20.dp)
                                    ) {
                                        items(viewModel.getReservedTimeSlots()) { item ->
                                            TimeSlotDetailsItemView(
                                                viewState = item
                                            )
                                            Spacer(modifier = Modifier.height(20.dp))
                                        }
                                    }
                                } else {
                                    Text(
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        text = "У даному закладі немає бронювань",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = colorResource(
                                                id = R.color.dark_gray
                                            )
                                        )
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height = 2.dp)
                                    .background(colorResource(id = R.color.main_yellow))
                            )
                        }
                    }
                }
            }
        }
    }
}