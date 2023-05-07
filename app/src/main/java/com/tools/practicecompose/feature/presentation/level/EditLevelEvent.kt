package com.tools.practicecompose.feature.presentation.level

import com.tools.practicecompose.feature.domain.model.NoteLevel

sealed class EditLevelEvent {
    data class EditLevel(val value: NoteLevel) : EditLevelEvent()
}
