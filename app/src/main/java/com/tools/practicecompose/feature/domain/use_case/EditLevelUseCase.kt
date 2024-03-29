package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.repository.MainRepository

class EditLevelUseCase(
    private val repository: MainRepository
) {
    suspend fun invoke(noteLevel: NoteLevel) {
        repository.editLevel(noteLevel)
    }
}