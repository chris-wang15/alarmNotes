package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class GetLevelMapUseCase(
    private val repository: MainRepository
) {
    fun invoke(): Flow<Map<Int, NoteLevel>> {
        return repository.getLevelColorMap()
    }
}