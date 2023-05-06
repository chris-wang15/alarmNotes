package com.tools.practicecompose.di

import android.app.Application
import androidx.room.Room
import com.tools.practicecompose.feature.repository.data_base.NoteDataBase
import com.tools.practicecompose.feature.repository.NoteRepository
import com.tools.practicecompose.feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDataBase): NoteRepository {
        return NoteRepository(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
            editLevelUseCase = EditLevelUseCase(repository),
            getLevelColorUseCase = GetLevelColorUseCase(repository),
        )
    }
}