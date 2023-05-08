package com.tools.practicecompose.feature.presentation.edit_page

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tools.practicecompose.feature.domain.model.InvalidNoteException
import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter content..."
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _readOnlyMode = mutableStateOf(true)
    val readOnlyMode: State<Boolean> = _readOnlyMode

    private val _noteLevel = mutableStateOf(1)
    val noteLevel: State<Int> = _noteLevel

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _noteReminder: MutableState<Long?> = mutableStateOf(null)
    val noteReminder: State<Long?> = _noteReminder

    private var currentNoteId: Int? = null

    init {
        val noteId = savedStateHandle.get<Int>("noteId") ?: -1
        if (noteId != -1) {
            _readOnlyMode.value = true
            viewModelScope.launch {
                noteUseCase.getNoteByIdUseCase.invoke(noteId)?.also { node ->
                    currentNoteId = node.id
                    _noteTitle.value = noteTitle.value.copy(
                        text = node.title,
                        isHintVisible = false,
                    )
                    _noteContent.value = noteContent.value.copy(
                        text = node.content,
                        isHintVisible = false,
                    )
                    _noteLevel.value = node.level
                    _noteReminder.value = node.remindTime
                }
            }
        } else {
            _readOnlyMode.value = false
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnterTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnterContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeLevel -> {
                _noteLevel.value = event.level
            }

            is AddEditNoteEvent.ExitAndCheckSaveState -> {
                viewModelScope.launch {
                    if (readOnlyMode.value) {
                        _eventFlow.emit(UiEvent.NavigateBack)
                    } else {
                        _eventFlow.emit(
                            UiEvent.NavigateBackWithErrorMsg("Exit without saving")
                        )
                    }
                }
            }

            AddEditNoteEvent.ChangeReadModeState -> {
                if (!readOnlyMode.value) {
                    // save note
                    viewModelScope.launch {
                        try {
                            val note = Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                level = noteLevel.value,
                                id = currentNoteId,
                                remindTime = noteReminder.value,
                            )
                            // add note
                            Log.d(TAG, "Add Note level ${note.level}")
                            noteUseCase.addNoteUseCase.invoke(note)
                            // turn on or turn off reminder
                            noteUseCase.setReminderUseCase.invoke(note)
                            _readOnlyMode.value = !readOnlyMode.value
                        } catch (e: InvalidNoteException) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = e.message ?: "Can not save note"
                                )
                            )
                        }
                    }
                } else {
                    _readOnlyMode.value = !readOnlyMode.value
                }
            }

            is AddEditNoteEvent.ChangeReminderState -> {
                if (event.enable) {
                    _noteReminder.value = event.remindTime
                } else {
                    _noteReminder.value = null
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavigateBack : UiEvent()
        data class NavigateBackWithErrorMsg(val message: String) : UiEvent()
    }
}

private const val TAG = "AddEditNoteScreen"