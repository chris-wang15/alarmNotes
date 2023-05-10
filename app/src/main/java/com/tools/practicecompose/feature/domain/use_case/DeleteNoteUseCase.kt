package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.repository.MainRepository

class DeleteNoteUseCase(
    private val repository: MainRepository
) {
    suspend fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}