package com.project.presentation.ui.screens.add_food_establishments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep.MainInfo
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep.Photos
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep.Tables
import com.project.presentation.ui.view.register_food_establishment.MainInfoView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodEstablishmentsScreen(
    modifier: Modifier = Modifier,
    viewModel: AddFoodEstablishmentsScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = viewModel.getTitle(),
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            ProgressStep(uiState.getProgress())
            when (uiState.currentStep) {
                MainInfo -> MainInfoView(
                    viewState = uiState.mainInfoViewState,
                    onNameChanged = viewModel::onMainInfoNameChanged,
                    onFoodEstablishmentTypeChanged = viewModel::onMainInfoFoodEstablishmentTypeChanged
                )

                Tables -> TODO()
                Photos -> TODO()
            }
        }
    }
}

@Composable
private fun ProgressStep(progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .height(5.dp),
            progress = progress,
            color = colorResource(id = R.color.main_yellow),
            trackColor = colorResource(
                id = R.color.gray
            )
        )
    }
}