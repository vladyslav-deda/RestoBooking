package com.project.presentation.ui.screens.add_food_establishments

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep.AddFoodEstablishmentMainInfo
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep.AddFoodEstablishmentPhotos
import com.project.presentation.ui.screens.add_food_establishments.model.AddFoodEstablishmentStep.AddFoodEstablishmentTables
import com.project.presentation.ui.view.common.LoadingView
import com.project.presentation.ui.view.register_food_establishment.AddPhotoView
import com.project.presentation.ui.view.register_food_establishment.MainInfoView
import com.project.presentation.ui.view.register_food_establishment.AddTagsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodEstablishmentsScreen(
    modifier: Modifier = Modifier,
    viewModel: AddFoodEstablishmentsScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToProfileScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.getTitle(),
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black),
                navigationIcon = {
                    IconButton(onClick = {
                        if (uiState.currentStep.stepNumber == 1) {
                            navigateBack()
                        } else {
                            viewModel.decreaseStepNumber()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = null
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
            if (uiState.isLoading) {
                LoadingView()
            } else if (uiState.isError) {
                Toast.makeText(
                    context,
                    "Something went wrong, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Crossfade(targetState = uiState.currentStep, label = "") {
                when (it) {
                    AddFoodEstablishmentMainInfo -> MainInfoView(
                        viewState = uiState.mainInfoViewState,
                        onNameChanged = viewModel::onMainInfoNameChanged,
                        onFoodEstablishmentTypeChanged = viewModel::onMainInfoTypeChanged,
                        onAddressChanged = viewModel::onMainInfoAddressChanged,
                        onCityChanged = viewModel::onMainInfoCityChanged,
                        onDescriptionChanged = viewModel::onMainInfoDescriptionChanged,
                        onToTimeSelected = viewModel::onMainInfoTimeToSelected,
                        onFromTimeSelected = viewModel::onMainInfoTimeFromSelected,
                        onPhoneForReservationChanged = viewModel::onMainInfoPhoneForReservationChanged,
                        onContinueClicked = viewModel::onContinueClicked
                    )

                    AddFoodEstablishmentTables -> AddTagsView(
                        viewState = uiState.addTagsViewState,
                        addTagToTheList = viewModel::addTagToSelected,
                        removeTagFromTheList = viewModel::removeTagFromSelected,
                        setValueForAddNewTagClicked = viewModel::setValueForAddNewTagClicked,
                        onContinueClicked = viewModel::onContinueClicked
                    )

                    AddFoodEstablishmentPhotos -> AddPhotoView(
                        viewState = uiState.addPhotoViewState,
                        onPhotoChanged = viewModel::changePhoto,
                        onRegisterClicked = viewModel::onRegisterClicked
                    )
                }
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