package com.project.presentation.ui.screens.pdp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.domain.model.Comment
import com.project.presentation.R
import com.project.presentation.ui.screens.pdp.view.AddCommentView
import com.project.presentation.ui.screens.pdp.view.PhotoSliderView
import com.project.presentation.ui.view.RatingBar
import com.project.presentation.ui.view.common.LoadingView
import com.project.presentation.ui.view.common.TagsView
import timber.log.Timber
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdpScreen(
    modifier: Modifier = Modifier,
    viewModel: PdpScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToReservation: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.showAddCommentDialog) {
        AddCommentView(
            onDismissDialog = { viewModel.showAddCommentDialog(false) },
            onAddClicked = { comment, rating -> viewModel.addComment(comment, rating) }
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
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .align(Alignment.TopCenter)
                    ) {
                        PhotoSliderView(
                            photos = uiState.foodEstablishment?.photoList ?: emptyList()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FoodEstablishmentDetailsView(
                            name = uiState.foodEstablishment?.name ?: "",
                            rating = uiState.foodEstablishment?.rating ?: 0.0f,
                            tags = uiState.foodEstablishment?.tags ?: emptyList(),
                            city = uiState.foodEstablishment?.city ?: "",
                            address = uiState.foodEstablishment?.address ?: "",
                            phoneForBooking = uiState.foodEstablishment?.phoneForBooking ?: "",
                            workingTime = viewModel.getWorkingHours(),
                            description = uiState.foodEstablishment?.description ?: "",
                            comments = uiState.foodEstablishment?.comments ?: emptyList()
                        ) {
                            viewModel.showAddCommentDialog(true)
                        }
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        Divider(
                            color = colorResource(id = R.color.main_yellow),
                            thickness = 1.dp
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                        ) {
                            Button(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                onClick = {
                                    navigateToReservation(viewModel.getFoodEstablishmentId())
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(
                                        id = R.color.main_yellow
                                    )
                                )
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = "Забронювати",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FoodEstablishmentDetailsView(
    modifier: Modifier = Modifier,
    name: String,
    rating: Float,
    tags: List<String>,
    city: String,
    address: String,
    phoneForBooking: String,
    workingTime: String,
    description: String,
    comments: List<Comment>,
    onAddCommentClicked: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
        )
        if (rating > 0) {
            Spacer(modifier = Modifier.height(12.dp))
            RatingBar(
                rating = rating
            )
        }
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
            TextButton(
                onClick = {
                    val u = Uri.parse("tel:$phoneForBooking")
                    val i = Intent(Intent.ACTION_DIAL, u)
                    try {
                        context.startActivity(i)
                    } catch (s: SecurityException) {
                        Timber.e(s)
                    }
                },
                contentPadding = PaddingValues(),
            ) {
                Text(
                    text = phoneForBooking,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                )
            }
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
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                modifier = Modifier
                    .size(15.dp),
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = colorResource(id = R.color.main_yellow)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Відгуки",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black)
            )
            TextButton(
                onClick = onAddCommentClicked,
                contentPadding = PaddingValues(),
            ) {
                Text(
                    text = "+ Додати відгук",
                    style = MaterialTheme.typography.bodyMedium.copy(color = colorResource(id = R.color.main_yellow))
                )
            }
        }
        if (comments.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_dialog),
                    contentDescription = null,
                    tint = colorResource(id = R.color.main_yellow)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Жодного відгука не було знайдено, станьте першим та поділіться враженнями про цей заклад.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column {
                comments.forEachIndexed { index, comment ->
                    CommentView(comment = comment)
                    if (comments.lastIndex != index) {
                        Divider(
                            color = colorResource(id = R.color.main_yellow),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentView(
    modifier: Modifier = Modifier,
    comment: Comment
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = comment.author,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
            )
            Text(
                text = DateMapper.mapDate(comment.dateAdded),
                style = MaterialTheme.typography.bodySmall.copy(color = colorResource(id = R.color.gray))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(
            rating = comment.rating.toFloat(),
            starSize = 12
        )
        comment.commentText?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 16777215
)
@Composable
fun FoodEstablishmentDetailsViewPreview() {
    val currentDate = Calendar.getInstance().timeInMillis
    FoodEstablishmentDetailsView(
        name = "Патріот",
        rating = 3.7f,
        tags = listOf("З дітьми", "Чисто", "Караоке", "Затишно"),
        city = "Вінниця",
        address = "вул. Прибережна 23",
        phoneForBooking = "0963456788",
        workingTime = "10:00-23:00",
        description = "Чудовий заклад для відпочинку з друзями та у великій компанії",
        comments = listOf(
            Comment(author = "Vladyslav Deda", commentText = "чудовий заклад", 3, currentDate),
            Comment(author = "Vladyslav Deda", commentText = "чудовий заклад", 3, currentDate),
            Comment(author = "Vladyslav Deda", commentText = "чудовий заклад", 3, currentDate)
        ),
        onAddCommentClicked = {}
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 16777215
)
@Composable
fun CommentViewPreview() {
    val currentDate = Calendar.getInstance().timeInMillis
    CommentView(
        comment = Comment(author = "Vladyslav Deda", commentText = "чудовий заклад", 3, currentDate)
    )
}