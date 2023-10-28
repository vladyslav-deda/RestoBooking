package com.project.presentation.ui.screens.pdp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.screens.pdp.view.PhotoSliderView
import com.project.presentation.ui.view.common.LoadingView
import com.project.presentation.ui.view.common.TagsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdpScreen(
    modifier: Modifier = Modifier,
    viewModel: PdpScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit
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
            if (uiState.isLoading) {
                LoadingView()
            } else {
                Column {
                    PhotoSliderView(photos = uiState.foodEstablishment?.photoList ?: emptyList())
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodEstablishmentDetailsView(
                        name = uiState.foodEstablishment?.name ?: "",
                        tags = uiState.foodEstablishment?.tags ?: emptyList(),
                        city = uiState.foodEstablishment?.city ?: "",
                        address = uiState.foodEstablishment?.address ?: "",
                        phoneForBooking = uiState.foodEstablishment?.phoneForBooking ?: "",
                        workingTime = viewModel.getWorkingHours()
                    )
                }
            }
        }
    }
}

@Composable
private fun FoodEstablishmentDetailsView(
    modifier: Modifier = Modifier,
    name: String,
    tags: List<String>,
    city: String,
    address: String,
    phoneForBooking: String,
    workingTime:String
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(12.dp))
        TagsView(tags = tags)
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
                text = "$city, $address",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(15.dp),
                imageVector = Icons.Outlined.Call,
                contentDescription = null,
                tint = colorResource(id = R.color.main_yellow)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = phoneForBooking,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(15.dp),
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = null,
                tint = colorResource(id = R.color.main_yellow)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = workingTime,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
    }
}


@Preview
@Composable
fun FoodEstablishmentDetailsViewPreview() {
    FoodEstablishmentDetailsView(
        name = "Патріот",
        tags = listOf("З дітьми", "Чисто", "Караоке", "Затишно"),
        city = "Вінниця",
        address = "вул. Прибережна 23",
        phoneForBooking = "0963456788",
        workingTime="10:00-23:00"
    )
}