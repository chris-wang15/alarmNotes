package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}