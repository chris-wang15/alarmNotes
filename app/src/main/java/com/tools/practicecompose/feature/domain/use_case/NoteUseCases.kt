package com.tools.practicecompose.feature.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val editLevelUseCase: EditLevelUseCase,
    val getLevelColorUseCase: GetLevelColorUseCase,
    val setReminderUseCase: SetReminderUseCase,
)
