package com.tools.practicecompose.feature.presentation.edit_page

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnterTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class EnterContent(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeLevel(val level: Int) : AddEditNoteEvent()
    object ChangeReadModeState: AddEditNoteEvent()
    object SaveNoteAndExit: AddEditNoteEvent()
    data class ChangeReminderState(val remindTime: Long, val enable: Boolean) : AddEditNoteEvent()
}
