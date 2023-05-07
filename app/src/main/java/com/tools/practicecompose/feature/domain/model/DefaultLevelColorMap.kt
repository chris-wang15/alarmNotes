package com.tools.practicecompose.feature.domain.model

import androidx.compose.ui.graphics.toArgb
import com.tools.practicecompose.ui.theme.*

const val LEVEL_ZERO = 0
const val LEVEL_ONE = 1
const val LEVEL_TWO = 2
const val LEVEL_THREE = 3
const val LEVEL_FOUR = 4

val defaultLevelColorMap: Map<Int, NoteLevel> = mapOf(
    LEVEL_ZERO to NoteLevel(LEVEL_ZERO, "LEVEL_ZERO", RedPink.toArgb()),
    LEVEL_ONE to NoteLevel(LEVEL_ONE, "LEVEL_ONE", RedOrange.toArgb()),
    LEVEL_TWO to NoteLevel(LEVEL_TWO, "LEVEL_TWO", LightGreen.toArgb()),
    LEVEL_THREE to NoteLevel(LEVEL_THREE, "LEVEL_THREE", Violet.toArgb()),
    LEVEL_FOUR to NoteLevel(LEVEL_FOUR, "LEVEL_FOUR", BabyBlue.toArgb()),
)

val defaultLevelKeyList = listOf(
    LEVEL_ZERO, LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, LEVEL_FOUR
)