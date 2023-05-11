package com.tools.practicecompose.feature.presentation.edit_page.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tools.practicecompose.feature.presentation.TestTag
import com.tools.practicecompose.feature.presentation.edit_page.AddEditNoteEvent
import com.tools.practicecompose.feature.presentation.edit_page.AddEditNoteViewModel
import com.tools.practicecompose.feature.presentation.level.LevelViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    levelViewModel: LevelViewModel = hiltViewModel(),
) {
    val levelState = viewModel.noteLevel
    val colorMapState = levelViewModel.state
    val readOnly = viewModel.readOnlyMode
    val snackbarHostState = remember { SnackbarHostState() }
//    val backgroundAnimatable = remember {
//        val level = colorMapState.value[levelState.value]
//        if (level == null) {
//            Log.e(TAG, "Can not get level for ${levelState.value}")
//        }
//        Animatable(Color(level?.colorInt ?: RedOrange.toArgb()))
//    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is AddEditNoteViewModel.UiEvent.NavigateBack -> {
                    navController.navigateUp()
                }
                is AddEditNoteViewModel.UiEvent.NavigateBackWithErrorMsg -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = { ChangeReadModeButton(viewModel) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(
                            colorMapState.value[levelState.value]?.colorInt ?: 0x000000
                        )
                    )
                    .padding(16.dp)
                    .padding(top = padding.calculateTopPadding())
                    .testTag(TestTag.EditScreen)
            ) {
                // title text
                BackButtonAndTitle(
                    viewModel = viewModel,
                )
                Spacer(modifier = Modifier.height(16.dp))

                LevelSelector(
                    viewModel = viewModel,
                    levelModel = levelViewModel,
                    scope = scope,
                )

                Spacer(modifier = Modifier.height(16.dp))
                if (readOnly.value) {
                    val timeStamp = viewModel.noteReminder.value
                    if (timeStamp != null) {
                        val c = Calendar.getInstance()
                        c.timeInMillis = timeStamp
                        val year = c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        val hour = c[Calendar.HOUR_OF_DAY]
                        val minute = c[Calendar.MINUTE]
                        Text(
                            text = "Reminder: $year - ${month + 1} - $day & $hour : $minute",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                } else if (!readOnly.value) {
                    TimeSelector(viewModel = viewModel)
                }

                // conent text
                Spacer(modifier = Modifier.height(16.dp))
                ContentTextField(viewModel = viewModel)
            }
        }
    )
}

@Composable
private fun ChangeReadModeButton(
    viewModel: AddEditNoteViewModel,
) {
    FloatingActionButton(
        onClick = {
            viewModel.onEvent(AddEditNoteEvent.ChangeReadModeState)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Icon(
            imageVector = if (viewModel.readOnlyMode.value) {
                Icons.Default.Edit
            } else Icons.Default.Save,
            contentDescription = "Change Read | Write Mode"
        )
    }
}

@Composable
private fun LevelSelector(
    viewModel: AddEditNoteViewModel,
    levelModel: LevelViewModel,
    scope: CoroutineScope,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val readOnlyState = viewModel.readOnlyMode
        val colorMap = levelModel.state.value
        if (!readOnlyState.value) {
            colorMap.keys.forEach { levelId ->
                val colorInt = colorMap[levelId]?.colorInt ?: 0x000000
                Box(modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(colorInt))
                    .border(
                        width = 3.dp,
                        color = if (viewModel.noteLevel.value == levelId) {
                            Color.Black
                        } else Color.Transparent,
                        shape = CircleShape,
                    )
                    .clickable {
//                        scope.launch {
//                            backgroundAnimatable.animateTo(
//                                targetValue = Color(colorInt),
//                                animationSpec = tween(durationMillis = 500)
//                            )
//                        }
                        viewModel.onEvent(AddEditNoteEvent.ChangeLevel(levelId))
                    })
            }
        }
    }
}

@Composable
private fun BackButtonAndTitle(
    viewModel: AddEditNoteViewModel,
) {
    val titleState = viewModel.noteTitle
    val readOnlyState = viewModel.readOnlyMode
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { viewModel.onEvent(AddEditNoteEvent.ExitAndCheckSaveState) },
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Sort"
            )
        }
        Spacer(modifier = Modifier.width(4.dp))

        TransparentHintTextField(
            text = titleState.value.text,
            hint = titleState.value.hint,
            readOnly = readOnlyState.value,
            onValueChange = {
                viewModel.onEvent(AddEditNoteEvent.EnterTitle(it))
            },
            onFocusChange = {
                viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
            },
            isHintVisible = titleState.value.isHintVisible,
            singleLine = true,
            textStyle = MaterialTheme.typography.headlineLarge,
            textFieldTestTag = TestTag.EditScreenTitle,
        )
    }
}

@Composable
private fun ContentTextField(
    viewModel: AddEditNoteViewModel,
) {
    val readOnlyState = viewModel.readOnlyMode
    val contentState = viewModel.noteContent
    TransparentHintTextField(
        text = contentState.value.text,
        hint = contentState.value.hint,
        readOnly = readOnlyState.value,
        onValueChange = {
            viewModel.onEvent(AddEditNoteEvent.EnterContent(it))
        },
        onFocusChange = {
            viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
        },
        isHintVisible = contentState.value.isHintVisible,
        singleLine = false,
        textStyle = MaterialTheme.typography.bodySmall,
        modifier = Modifier.fillMaxHeight(),
        textFieldTestTag = TestTag.EditScreenContent,
    )
}

private const val TAG = "AddEditNoteScreen"