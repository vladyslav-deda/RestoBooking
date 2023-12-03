package com.project.presentation.ui.screens.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.project.domain.repository.SurveyData
import com.project.presentation.R
import com.project.presentation.ui.view.common.LoadingView
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Статистика закладу",
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
                            .padding(horizontal = 20.dp)
                    ) {

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            buildAnnotatedString {
                                append("На питання ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(SurveyData.getData()[0].question)
                                }
                                append(" користувачі відповіли:")
                            }
                        )
                        val totalFirst = uiState.firstQuestionPair?.first?.plus(
                            uiState.firstQuestionPair?.second ?: 0
                        ) ?: 0
                        val percentageYesFirst =
                            (uiState.firstQuestionPair?.first?.toDouble()?.div(totalFirst))?.times(
                                100
                            )
                        val percentageNoFirst = (uiState.firstQuestionPair?.second?.toDouble()
                            ?.div(totalFirst))?.times(100)

                        Spacer(modifier = Modifier.height(10.dp))
                        PieChart(
                            pieChartData = PieChartData(
                                listOf(
                                    PieChartData.Slice(
                                        percentageYesFirst?.toFloat() ?: 0f,
                                        colorResource(id = R.color.green)
                                    ),
                                    PieChartData.Slice(
                                        percentageNoFirst?.toFloat() ?: 0f,
                                        colorResource(id = R.color.red)
                                    )
                                )
                            ),
                            modifier = Modifier.size(200.dp),
                            animation = simpleChartAnimation(),
                            sliceDrawer = SimpleSliceDrawer()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        AnswersDescription(percentageYesFirst, percentageNoFirst)
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            buildAnnotatedString {
                                append("На питання ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(SurveyData.getData()[1].question)
                                }
                                append(" користувачі відповіли:")
                            }
                        )
                        val totalSecond = uiState.secondQuestionPair?.first?.plus(
                            uiState.secondQuestionPair?.second ?: 0
                        ) ?: 0
                        val percentageYesSecond =
                            (uiState.secondQuestionPair?.first?.toDouble()
                                ?.div(totalSecond))?.times(
                                    100
                                )
                        val percentageNoSecond = (uiState.secondQuestionPair?.second?.toDouble()
                            ?.div(totalSecond))?.times(100)

                        Spacer(modifier = Modifier.height(10.dp))
                        PieChart(
                            pieChartData = PieChartData(
                                listOf(
                                    PieChartData.Slice(
                                        percentageYesSecond?.toFloat() ?: 0f,
                                        colorResource(id = R.color.green)
                                    ),
                                    PieChartData.Slice(
                                        percentageNoSecond?.toFloat() ?: 0f,
                                        colorResource(id = R.color.red)
                                    )
                                )
                            ),
                            modifier = Modifier.size(200.dp),
                            animation = simpleChartAnimation(),
                            sliceDrawer = SimpleSliceDrawer()
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        AnswersDescription(percentageYesSecond, percentageNoSecond)
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            buildAnnotatedString {
                                append("На питання ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(SurveyData.getData()[2].question)
                                }
                                append(" користувачі відповіли:")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        BarChart(
                            barChartData = BarChartData(
                                bars = listOf(
                                    BarChartData.Bar(
                                        label = "1",
                                        value = uiState.thirdList[0],
                                        color = colorResource(id = R.color.violet)
                                    ),
                                    BarChartData.Bar(
                                        label = "2",
                                        value = uiState.thirdList[1],
                                        color = colorResource(id = R.color.blue)
                                    ),
                                    BarChartData.Bar(
                                        label = "3",
                                        value = uiState.thirdList[2],
                                        color = colorResource(id = R.color.malin)
                                    ),
                                    BarChartData.Bar(
                                        label = "4",
                                        value = uiState.thirdList[3],
                                        color = colorResource(id = R.color.light_green)
                                    ),
                                    BarChartData.Bar(
                                        label = "5",
                                        value = uiState.thirdList[4],
                                        color = colorResource(id = R.color.coral)
                                    )
                                )
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                .height(250.dp),
                            animation = simpleChartAnimation(),
                            barDrawer = SimpleBarDrawer(),
                            xAxisDrawer = SimpleXAxisDrawer(),
                            yAxisDrawer = SimpleYAxisDrawer(
                                labelRatio = 100,
                                labelValueFormatter = {
                                    it.toInt().toString()
                                }
                            ),
                            labelDrawer = SimpleValueDrawer(
                                drawLocation = SimpleValueDrawer.DrawLocation.XAxis,
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Column {
                            TextDescriptionForBarChart(
                                number = "1",
                                value = SurveyData.getData()[2].answers[0]
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            TextDescriptionForBarChart(
                                number = "2",
                                value = SurveyData.getData()[2].answers[1]
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            TextDescriptionForBarChart(
                                number = "3",
                                value = SurveyData.getData()[2].answers[2]
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            TextDescriptionForBarChart(
                                number = "4",
                                value = SurveyData.getData()[2].answers[3]
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            TextDescriptionForBarChart(
                                number = "5",
                                value = SurveyData.getData()[2].answers[4]
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TextDescriptionForBarChart(
    number: String,
    value: String
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            ) {
                append(number)
            }
            append(" - $value")
        }
    )
}

@Composable
private fun AnswersDescription(percentageYes: Double?, percentageNo: Double?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(colorResource(id = R.color.green))
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = "Так (${percentageYes?.roundToInt()}%)",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(colorResource(id = R.color.red))
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = "Ні (${percentageNo?.roundToInt()}%)",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
        )
    }
}