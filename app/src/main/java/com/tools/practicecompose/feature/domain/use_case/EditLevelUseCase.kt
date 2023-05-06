package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.repository.NoteRepository

class EditLevelUseCase(
    private val repository: NoteRepository
) {
    suspend fun invoke(noteLevel: NoteLevel) {
        repository.editLevel(noteLevel)
    }
}