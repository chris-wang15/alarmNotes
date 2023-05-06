package com.tools.practicecompose.feature.domain.model

import com.tools.practicecompose.feature.domain.sort.level.LevelType

data class NoteLevel(
    val level: LevelType,
    val title: String,
    val colorInt: Int,
)