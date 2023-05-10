package com.tools.practicecompose.di

import android.app.Application
import androidx.room.Room
import com.tools.practicecompose.feature.repository.data_base.NoteDataBase
import com.tools.practicecompose.feature.repository.MainRepository
import com.tools.practicecompose.feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDataBase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDataBase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDataBase, app: Application): MainRepository {
        return MainRepository(db.noteDao, app)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(
        app: Application,
        repository: MainRepository
    ): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
            editLevelUseCase = EditLevelUseCase(repository),
            getLevelMapUseCase = GetLevelMapUseCase(repository),
            setReminderUseCase = SetReminderUseCase(app, repository),
            resetLevelUseCase = ResetLevelUseCase(repository),
        )
    }
}