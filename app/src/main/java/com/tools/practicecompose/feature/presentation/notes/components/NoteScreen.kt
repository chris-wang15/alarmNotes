package com.tools.practicecompose.feature.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tools.practicecompose.feature.presentation.Screen
import com.tools.practicecompose.feature.presentation.level.LevelViewModel
import com.tools.practicecompose.feature.presentation.notes.NotesEvent
import com.tools.practicecompose.feature.presentation.notes.NotesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    levelViewModel: LevelViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContainerColor = Color.Transparent,
        drawerContent = {
            DrawerContent(
                viewModel = viewModel,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
            )
        },
        content = {
            Scaffold(
//                floatingActionButton = {
//                    if (BuildConfig.DEBUG) {
//                        ShowTestButton(navController)
//                    }
//                },
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        TopBar(viewModel = viewModel)

                        // Expandable Sort Area
                        AnimatedVisibility(
                            visible = state.isOrderSectionVisible,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            OrderSection(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                noteOrder = state.noteOrder,
                                onOrderChange = {
                                    viewModel.onEvent(NotesEvent.Order(it))
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        NoteList(
                            navController = navController,
                            viewModel = viewModel,
                            levelViewModel = levelViewModel,
                            scope = scope,
                            snackbarHostState = snackbarHostState,
                        )
                    }
                    // No Result Text
                    if (state.note.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Slide Right And Add Your New Notes",
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier.fillMaxSize()
                                    .wrapContentHeight(), // used for text vertically center
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            )
        }
    )
}

@Composable
private fun TopBar(viewModel: NotesViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Notes List",
            style = MaterialTheme.typography.headlineMedium
        )
        IconButton(onClick = {
            viewModel.onEvent(NotesEvent.ToggleFilterSection)
        }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Sort")
        }
    }
}

@Composable
private fun NoteList(
    navController: NavController,
    viewModel: NotesViewModel,
    levelViewModel: LevelViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    val state = viewModel.state.value
    val levelMap = levelViewModel.state
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.note) { note ->
            NoteItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            Screen.AddEditNoteScreen.route
                                    + "?noteId=${note.id}&noteColor=${note.level}"
                        )
                    },
                color = Color(
                    levelMap.value[note.level]?.colorInt ?: 0x000000
                ),
                note = note,
                onDeleteClick = {
                    viewModel.onEvent(NotesEvent.DeleteNote(note))
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            "Note deleted",
                            actionLabel = "Undo"
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(NotesEvent.RestoreNote)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerContent(
    viewModel: NotesViewModel,
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .requiredWidth(250.dp)
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "Add Note") },
            label = { Text(text = "Add Notes") },
            selected = true,
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(Screen.AddEditNoteScreen.route)
            },
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Edit, contentDescription = "Edit Level") },
            label = { Text(text = "Edit Level") },
            selected = true,
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(Screen.EditLevelScreen.route)
            },
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            val filterContainerColor = MaterialTheme.colorScheme.secondaryContainer
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRoundRect(
                    color = filterContainerColor,
                    size = size,
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }

            FilterSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                noteOrder = state.noteOrder,
                onOrderChange = {
                    scope.launch { drawerState.close() }
                    viewModel.onEvent(NotesEvent.Order(it))
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
@Deprecated("Test Mode Only")
private fun ShowTestButton(
    navController: NavController,
) {
    FloatingActionButton(
        containerColor = Color.Transparent,
        onClick = {
            navController.navigate(Screen.AddEditNoteScreen.route)
        }
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Node")
    }
}
