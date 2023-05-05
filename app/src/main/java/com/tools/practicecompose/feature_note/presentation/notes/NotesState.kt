package com.tools.practicecompose.feature_note.presentation.notes

import com.tools.practicecompose.feature_note.domain.model.Note
import com.tools.practicecompose.feature_note.domain.util.NoteOrder
import com.tools.practicecompose.feature_note.domain.util.OrderType

data class NotesState(
    val note: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)