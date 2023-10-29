package com.project.presentation.ui.screens.srp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.domain.model.Photo
import com.project.presentation.R
import com.project.presentation.ui.view.SrpItemView
import com.project.presentation.ui.view.SrpItemViewState
import com.project.presentation.ui.view.common.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SrpScreen(
    modifier: Modifier = Modifier,
    viewModel: SrpViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToPdp: (foodEstablishmentId: String) -> Unit
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
            Column {
                ToolsView(
                    onSortingClick = { },
                    onFiltersClick = { }
                )
                if (uiState.isLoading) {
                    LoadingView()
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        itemsIndexed(uiState.list) { index, item ->
                            SrpItemView(
                                viewState = SrpItemViewState(
                                    photo = if (item.photoList.isNotEmpty()) {
                                        item.photoList[0]
                                    } else {
                                        Photo()
                                    },
                                    name = item.name,
                                    foodEstablishmentType = item.foodEstablishmentType,
                                    rating = item.rating,
                                    tags = item.tags
                                )
                            ) {
                                navigateToPdp(item.id)
                            }

                            if (index < uiState.list.lastIndex) {
                                Spacer(modifier = Modifier.height(20.dp))
                                Divider(
                                    color = colorResource(id = R.color.main_yellow),
                                    thickness = 1.dp
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }
//                    items(uiState.list) {
//                        SrpItemView(
//                            viewState = SrpItemViewState(
//                                photo = it.photoList[0],
//                                name = it.name,
//                                foodEstablishmentType = it.foodEstablishmentType,
//                                rating = it.rating,
//                                tags = it.tags
//                            )
//                        ) {
//
//                        }
//                    }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToolsView(
    modifier: Modifier = Modifier,
    onSortingClick: () -> Unit,
    onFiltersClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(colorResource(id = R.color.light_yellow))
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = onSortingClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = "sort icon"
                )
                Text(
                    text = stringResource(R.string.sorting),
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.Black)
                )
            }
            TextButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = onFiltersClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filters),
                    tint = colorResource(id = R.color.main_yellow),
                    contentDescription = "filter icon"
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = stringResource(R.string.filters),
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.Black)
                )
            }
        }
    }
    Divider(
        modifier = Modifier,
        color = colorResource(id = R.color.main_yellow),
        thickness = 2.dp
    )
}