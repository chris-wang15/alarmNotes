package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.InvalidNoteException
import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.repository.MainRepository

class AddNoteUseCase(
    private val repository: MainRepository
) {
    @kotlin.jvm.Throws(InvalidNoteException::class)
    suspend fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can not be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content can not be empty")
        }
        repository.insertNote(note)
    }
}