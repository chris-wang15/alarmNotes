package com.tools.practicecompose.feature.repository.alarm

import com.tools.practicecompose.feature.domain.model.Note

interface AlarmScheduler {
    fun schedule(item: Note)

    fun cancel(item: Note)
}