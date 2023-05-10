package com.tools.practicecompose.feature.domain.use_case

import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.sort.NoteOrder
import com.tools.practicecompose.feature.domain.sort.OrderType
import com.tools.practicecompose.feature.domain.sort.SortType
import com.tools.practicecompose.feature.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: MainRepository
) {

    fun invoke(
        noteOrder: NoteOrder = NoteOrder()
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                OrderType.ASCENDING -> {
                    when (noteOrder.sortType) {
                        SortType.TITLE -> notes.sortedBy { it.title.lowercase() }
                        SortType.DATE -> notes.sortedBy { it.timestamp }
                        SortType.LEVEL -> notes.sortedBy { it.level }
                        SortType.REMIND_TIME -> notes.filter { it.remindTime != null }
                            .sortedBy { it.remindTime }
                    }
                }
                OrderType.DESCENDING -> {
                    when (noteOrder.sortType) {
                        SortType.TITLE -> notes.sortedByDescending { it.title.lowercase() }
                        SortType.DATE -> notes.sortedByDescending { it.timestamp }
                        SortType.LEVEL -> notes.sortedByDescending { it.level }
                        SortType.REMIND_TIME -> notes.filter { it.remindTime != null }
                            .sortedByDescending { it.remindTime }
                    }
                }
            }
        }
    }
}