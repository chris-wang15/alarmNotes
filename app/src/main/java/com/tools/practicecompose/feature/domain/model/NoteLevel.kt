package com.tools.practicecompose.feature.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NoteLevel(
    @SerializedName("level")
    val level: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("colorId")
    val colorId: Int,
) {
    val colorInt = colorId.argb()
}