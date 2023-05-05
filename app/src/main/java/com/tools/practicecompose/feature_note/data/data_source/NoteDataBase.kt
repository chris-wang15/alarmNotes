package com.tools.practicecompose.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tools.practicecompose.feature_note.domain.model.Note

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