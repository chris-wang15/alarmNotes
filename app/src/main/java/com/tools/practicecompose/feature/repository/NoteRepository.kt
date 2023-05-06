package com.tools.practicecompose.feature.repository

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.domain.sort.level.LevelType
import com.tools.practicecompose.feature.repository.data_base.NoteDao
import kotlinx.coroutines.flow.Flow
import androidx.compose.ui.graphics.Color
import com.tools.practicecompose.feature.domain.model.defaultLevelColorMap

class NoteRepository(private val dao: NoteDao) {
    fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }

    suspend fun editLevel(noteLevel: NoteLevel) {

    }

    suspend fun getLevelColorMap(): Map<LevelType, Color> {
        return defaultLevelColorMap
    }
}