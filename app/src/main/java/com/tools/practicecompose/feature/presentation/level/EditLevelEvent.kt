package com.tools.practicecompose.feature.presentation.level

import com.tools.practicecompose.feature.domain.model.NoteLevel

sealed class EditLevelEvent {
    data class EditLevelAndDismissDialog(val value: NoteLevel) : EditLevelEvent()
    data class ShowColorPickerDialog(val value: NoteLevel) : EditLevelEvent()
    object ResetLevelInfo: EditLevelEvent()
}
