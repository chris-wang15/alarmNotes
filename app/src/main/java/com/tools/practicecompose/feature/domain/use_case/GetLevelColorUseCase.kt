package com.tools.practicecompose.feature.domain.use_case

import androidx.compose.ui.graphics.Color
import com.tools.practicecompose.feature.domain.sort.level.LevelType
import com.tools.practicecompose.feature.repository.NoteRepository

class GetLevelColorUseCase(
    private val repository: NoteRepository
) {
    suspend fun invoke(): Map<LevelType, Color> {
        return repository.getLevelColorMap()
    }
}