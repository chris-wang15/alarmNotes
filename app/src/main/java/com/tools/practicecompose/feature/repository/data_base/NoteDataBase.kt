package com.tools.practicecompose.feature.repository.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tools.practicecompose.feature.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDataBase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "note_db"
    }
}