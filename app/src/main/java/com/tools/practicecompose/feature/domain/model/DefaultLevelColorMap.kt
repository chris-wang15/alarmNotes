package com.tools.practicecompose.feature.domain.model

import androidx.compose.ui.graphics.toArgb
import com.tools.practicecompose.ui.theme.*

const val LEVEL_ZERO = 0
const val LEVEL_ONE = 1
const val LEVEL_TWO = 2
const val LEVEL_THREE = 3
const val LEVEL_FOUR = 4

val defaultLevelColorMap: Map<Int, NoteLevel> = mapOf(
    LEVEL_ZERO to NoteLevel(LEVEL_ZERO, "LEVEL_0", LightGreen.toArgb()),
    LEVEL_ONE to NoteLevel(LEVEL_ONE, "LEVEL_1", LightYellow.toArgb()),
    LEVEL_TWO to NoteLevel(LEVEL_TWO, "LEVEL_2", LightGrey.toArgb()),
    LEVEL_THREE to NoteLevel(LEVEL_THREE, "LEVEL_3", LightBlue.toArgb()),
    LEVEL_FOUR to NoteLevel(LEVEL_FOUR, "LEVEL_4", LightOrange.toArgb()),
)

val defaultLevelKeyList = listOf(
    LEVEL_ZERO, LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, LEVEL_FOUR
)

val selectableColorMap: Map<Int, Int> = mapOf(
    0 to LightGreen.toArgb(),
    1 to LightYellow.toArgb(),
    2 to LightGrey.toArgb(),
    3 to LightBlue.toArgb(),
    4 to LightOrange.toArgb(),
)