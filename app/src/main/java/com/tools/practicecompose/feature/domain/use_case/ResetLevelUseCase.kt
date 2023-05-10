package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.repository.MainRepository

class ResetLevelUseCase(
    private val repository: MainRepository
) {
    suspend fun invoke() {
        repository.resetLevel()
    }
}