package com.tools.practicecompose.feature.presentation.edit_page

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
)