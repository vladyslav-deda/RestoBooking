package com.project.presentation.ui.screens.pdp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.project.domain.repository.SurveyData
import com.project.presentation.R

@Composable
fun AddSurveyForStatistics(
    onDismissDialog: () -> Unit = {},
    onAddClicked: (Map<Int, Int>) -> Unit
) {
    var selected0 by remember { mutableIntStateOf(0) }
    var selected1 by remember { mutableIntStateOf(0) }
    var selected2 by remember { mutableIntStateOf(0) }

    Dialog(onDismissRequest = { onDismissDialog() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Дайте відповідь на декілька запитань",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val firstItem = SurveyData.getData()[0]
                    Text(
                        text = firstItem.question,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    RadioGroup(
                        items = firstItem.answers,
                        selected = firstItem.answers[selected0],
                        setSelected = {
                            selected0 = it
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val secondItem = SurveyData.getData()[1]
                    Text(
                        text = secondItem.question,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    RadioGroup(
                        items = secondItem.answers,
                        selected = secondItem.answers[selected1],
                        setSelected = {
                            selected1 = it
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val thirdItem = SurveyData.getData()[2]
                    Text(
                        text = thirdItem.question,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    RadioGroup(
                        items = thirdItem.answers,
                        selected = thirdItem.answers[selected2],
                        setSelected = {
                            selected2 = it
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            onClick = {
                                onAddClicked(
                                    mapOf(
                                        0 to selected0,
                                        1 to selected1,
                                        2 to selected2
                                    )
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow)),
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 22.dp),
                                text = stringResource(id = R.string.add),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadioGroup(
    items: List<String>,
    selected: String,
    setSelected: (selectedIndex: Int) -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == item,
                        onClick = {
                            setSelected(index)
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.main_yellow),
                            unselectedColor = colorResource(id = R.color.gray)
                        )
                    )
                    Text(text = item, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddSurveyForStatisticsPreview() {
    AddSurveyForStatistics(onDismissDialog = {}, onAddClicked = { t -> })
}