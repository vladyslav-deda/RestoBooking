package com.project.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.project.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTagDialog(
    onDismissDialog: () -> Unit,
    onAddClicked: (String) -> Unit
) {

    var newTag by remember {
        mutableStateOf<String?>(null)
    }
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
                        text = stringResource(id = R.string.add_tag),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = newTag ?: "",
                        modifier = Modifier
                            .fillMaxWidth(),
                        onValueChange = {
                            newTag = it
                        },
                        placeholder = { Text(text = stringResource(id = R.string.tag)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = colorResource(id = R.color.gray),
                            textColor = Color.Black,
                            placeholderColor = colorResource(id = R.color.gray),
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(
                            onClick = onDismissDialog
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = colorResource(
                                        id = R.color.dark_gray
                                    )
                                )
                            )
                        }
                        Button(
                            onClick = {
                                onAddClicked(newTag ?: "")
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_yellow)),
                            enabled = newTag?.isNotEmpty() == true
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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddTagDialogDialogPreview() {
    AddTagDialog(onDismissDialog = {}, onAddClicked = {})
}
