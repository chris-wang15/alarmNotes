package com.tools.practicecompose.feature.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tools.practicecompose.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long?,
    val endTime: Long,
    val level: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors: List<Color> = listOf(
            RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}

class InvalidNoteException(message: String): Exception(message)