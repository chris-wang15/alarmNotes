package com.tools.practicecompose.feature.domain.use_case

import android.content.Context
import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.repository.NoteRepository
import com.tools.practicecompose.feature.repository.alarm.MyAlarmScheduler

class SetReminderUseCase(
    private val context: Context,
    private val repository: NoteRepository
) {
    private val scheduler = MyAlarmScheduler(context)

    fun invoke(note: Note) {
        if (note.remindTime == null) {
            scheduler.cancel(note)
        } else {
            scheduler.schedule(note)
        }
    }
}