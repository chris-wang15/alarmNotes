package com.tools.practicecompose.feature.repository

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.domain.model.defaultLevelColorMap
import com.tools.practicecompose.feature.repository.data_base.NoteDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    // TODO
    suspend fun editLevel(noteLevel: NoteLevel) {
        delay(100)
    }

    // TODO
    fun getLevelColorMap(): Flow<Map<Int, NoteLevel>> = flow {
        emit(defaultLevelColorMap)
    }
}