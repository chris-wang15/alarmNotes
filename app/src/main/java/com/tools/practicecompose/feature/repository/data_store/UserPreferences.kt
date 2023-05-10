package com.tools.practicecompose.feature.repository.data_store

import com.google.gson.annotations.SerializedName
import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.domain.model.defaultLevelColorMap
import com.tools.practicecompose.ui.theme.*
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
//    @SerializedName("LEVEL_0_COLOR")
//    val LEVEL_0_COLOR: Int = LightGreen.toArgb(),
//    @SerializedName("LEVEL_1_COLOR")
//    val LEVEL_1_COLOR: Int = LightYellow.toArgb(),
//    @SerializedName("LEVEL_2_COLOR")
//    val LEVEL_2_COLOR: Int = LightGrey.toArgb(),
//    @SerializedName("LEVEL_3_COLOR")
//    val LEVEL_3_COLOR: Int = LightBlue.toArgb(),
//    @SerializedName("LEVEL_4_COLOR")
//    val LEVEL_4_COLOR: Int = LightOrange.toArgb(),

    @SerializedName("levelMap")
    val levelMap : Map<Int, NoteLevel> = defaultLevelColorMap
)