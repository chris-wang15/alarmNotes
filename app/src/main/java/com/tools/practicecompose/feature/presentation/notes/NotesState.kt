package com.tools.practicecompose.feature.presentation.notes

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.sort.NoteOrder
import com.tools.practicecompose.feature.domain.sort.OrderType

data class NotesState(
    val note: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder(),
    val isOrderSectionVisible: Boolean = false
)