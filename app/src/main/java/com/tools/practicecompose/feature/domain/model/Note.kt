package com.tools.practicecompose.feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long?,
    val remindTime: Long? = null,
    val level: Int = 0,
    @PrimaryKey val id: Int? = null
)

class InvalidNoteException(message: String): Exception(message)