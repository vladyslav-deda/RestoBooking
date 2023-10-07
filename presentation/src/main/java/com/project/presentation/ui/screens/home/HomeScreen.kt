package com.project.presentation.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.view.HomeSearchView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSrp: (String, List<String>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Log.i("myLogs", "HomeScreen: uiState.continueClicked = ${uiState.continueClicked}")
    if (uiState.continueClicked) {
        viewModel.resetContinueClickedStatus()
        navigateToSrp(
            uiState.homeSearchViewState.city.trim(),
            uiState.homeSearchViewState.tags.map { it.title }
        )
    }
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
                .fillMaxSize(),
        ) {
            HomeSearchView(
                viewState = uiState.homeSearchViewState,
                onCityChanged = viewModel::onCityChanged,
                handleTagClick = viewModel::handleTagSelection,
                onSearchClicked = viewModel::onSearchClicked
            )
        }
    }
}

