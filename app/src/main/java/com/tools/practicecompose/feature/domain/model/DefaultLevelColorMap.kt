package com.tools.practicecompose.feature.domain.model

import androidx.compose.ui.graphics.Color
import com.tools.practicecompose.feature.domain.sort.level.LevelType
import com.tools.practicecompose.ui.theme.*

val defaultLevelColorMap: Map<LevelType, Color> = mapOf(
    LevelType.LEVEL_ONE to RedOrange,
    LevelType.LEVEL_TWO to LightGreen,
    LevelType.LEVEL_THREE to Violet,
    LevelType.LEVEL_FOUR to BabyBlue,
    LevelType.LEVEL_FIVE to RedPink,
)