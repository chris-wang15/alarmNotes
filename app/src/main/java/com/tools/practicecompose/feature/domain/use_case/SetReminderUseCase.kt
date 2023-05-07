package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.repository.NoteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class SetReminderUseCase(
    private val repository: NoteRepository
) {
    suspend fun invoke(note: Note) {
        // TODO
        if (note.remindTime == null) {
            delay(100)
        } else {
            delay(99)
        }
    }
}