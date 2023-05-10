package com.tools.practicecompose.feature.presentation.level.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.presentation.level.EditLevelEvent
import com.tools.practicecompose.feature.presentation.level.LevelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelScreen(
    navController: NavController,
    viewModel: LevelViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val levelMapState = viewModel.state
    val levelKeyListState = viewModel.levelKeyList
    val editLevel = viewModel.editLevelOfPicker

    Scaffold(
        floatingActionButton = { ResetButton(viewModel)},
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigateUp() },
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Sort")
                }
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Note Levels",
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = padding.calculateTopPadding())
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(levelKeyListState.value) { key ->
                        val data = levelMapState.value[key]
                        if (data == null) {
                            Log.e(TAG, "Can not get key: $key")
                        } else {
                            LevelItem(
                                modifier = Modifier.fillMaxWidth(),
                                viewModel = viewModel,
                                noteLevel = data,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            if (editLevel.value != null) {
                ColorPickerDialog(
                    initId = editLevel.value?.colorId ?: 0,
                    onDismiss = {selectedColorId->
                    val level = editLevel.value ?: return@ColorPickerDialog
                    viewModel.onEvent(EditLevelEvent.EditLevelAndDismissDialog(
                        level.copy(colorId = selectedColorId)
                    ))
                })
            }
        }
    )
}

@Composable
private fun LevelItem(
    modifier: Modifier = Modifier,
    viewModel: LevelViewModel,
    noteLevel: NoteLevel
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .shadow(15.dp, CircleShape)
                .clip(CircleShape)
                .background(Color(noteLevel.colorInt))
                .border(
                    width = 3.dp,
                    color = Color.Transparent,
                    shape = CircleShape,
                )
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = noteLevel.title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            onClick = {
                viewModel.onEvent(EditLevelEvent.ShowColorPickerDialog(noteLevel))
            }
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Level Detail"
            )
        }
    }
}

@Composable
private fun ResetButton(viewModel: LevelViewModel) {
    FloatingActionButton(
        containerColor = Color.DarkGray,
        onClick = {
            viewModel.onEvent(EditLevelEvent.ResetLevelInfo)
        }
    ) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset Level Color")
    }
}

private const val TAG = "LevelScreen"