package com.project.presentation.ui.view.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.domain.model.Comment
import com.project.presentation.R
import com.project.presentation.ui.screens.pdp.DateMapper
import com.project.presentation.ui.view.RatingBar
import java.util.Calendar


@Composable
fun CommentView(
    modifier: Modifier = Modifier,
    comment: Comment,
    isEnableToAnswer: Boolean = false,
    onReplyClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(vertical = 12.dp)
    ) {
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
                style = MaterialTheme.typography.bodyMedium.copy(color = colorResource(id = R.color.dark_gray))
            )
        }
        if (isEnableToAnswer && comment.textOfReplyToComment.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onReplyClicked,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = colorResource(id = R.color.main_yellow)
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(15.dp),
                            painter = painterResource(id = R.drawable.ic_reply),
                            contentDescription = null,
                            tint = colorResource(id = R.color.main_yellow)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Відповісти на коментар",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(
                                    id = R.color.dark_gray
                                )
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        } else {
            Row {
                Spacer(modifier = Modifier.width(30.dp))
                Column {

                    Spacer(modifier = Modifier.height(8.dp))
                    if (comment.textOfReplyToComment.isNullOrEmpty()) {
                        Text(
                            text = "На жаль, заклад не відповів на даний коментар",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(
                                    id = R.color.red
                                )
                            )
                        )
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Відповідь закладу",
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                            )
                            comment.dateOfReply?.let {
                                Text(
                                    text = DateMapper.mapDate(it),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = colorResource(
                                            id = R.color.gray
                                        )
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        comment.textOfReplyToComment?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = colorResource(
                                        id = R.color.dark_gray
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 16777215
)
@Composable
fun CommentViewPreview() {
    val currentDate = Calendar.getInstance().timeInMillis
    CommentView(
        comment = Comment(
            id = "",
            author = "Vladyslav Deda",
            commentText = "чудовий заклад",
            3,
            currentDate
        ),
        isEnableToAnswer = true
    )
}