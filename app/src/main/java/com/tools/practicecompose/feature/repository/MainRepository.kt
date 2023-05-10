package com.tools.practicecompose.feature.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.domain.model.defaultLevelColorMap
import com.tools.practicecompose.feature.repository.data_base.NoteDao
import com.tools.practicecompose.feature.repository.data_store.PreferencesSerializer
import com.tools.practicecompose.feature.repository.data_store.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by dataStore("app-settings.json", PreferencesSerializer)

class MainRepository(private val dao: NoteDao, private val app: Application) {

    private val dataStore: DataStore<UserPreferences> by lazy {
        app.dataStore
    }

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
        try {
            dataStore.updateData {
                val levelMap = HashMap(it.levelMap)
                levelMap[noteLevel.level] = noteLevel
                it.copy(
                    levelMap = levelMap
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "error when edit level", e)
        }
    }

    suspend fun resetLevel() {
        try {
            dataStore.updateData {
                it.copy(
                    levelMap = defaultLevelColorMap
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "error when reset level", e)
        }
    }

    fun getLevelColorMap(): Flow<Map<Int, NoteLevel>> {
        return dataStore.data.map {
            it.levelMap
        }
    }
}

private const val TAG = "MainRepository"