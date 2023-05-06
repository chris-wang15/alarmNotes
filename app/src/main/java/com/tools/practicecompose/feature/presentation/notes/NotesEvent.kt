package com.tools.practicecompose.feature.presentation.notes

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.sort.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}