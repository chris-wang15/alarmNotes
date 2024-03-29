package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.repository.MainRepository

class GetNoteByIdUseCase(
    private val repository: MainRepository
) {
    suspend fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}