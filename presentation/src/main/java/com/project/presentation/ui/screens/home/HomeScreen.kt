package com.project.presentation.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.project.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
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
        Column(
            modifier = modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            if (uiState.list.isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(uiState.list[0].photoList) { item ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(110.dp)
                        ) {
                            if (item.uri != null) {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = ImageRequest.Builder(LocalContext.current).data(item.uri)
                                        .crossfade(enable = true).build(),
                                    contentDescription = "Avatar Image",
                                    contentScale = ContentScale.Crop,
                                )
                            } else {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_plus),
                                        modifier = Modifier
                                            .size(50.dp),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.gray)
                                    )
                                }
                            }
                        }
                    }
                }
                }

            Button(onClick = { viewModel.fetch() }) {
                Text(text = "Fetch")
            }
        }
    }
}