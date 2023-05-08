package com.tools.practicecompose.feature.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tools.practicecompose.feature.domain.sort.NoteOrder
import com.tools.practicecompose.feature.domain.sort.OrderType
import com.tools.practicecompose.feature.domain.sort.SortType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder(),
    onOrderChange: (noteOrder: NoteOrder) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType == OrderType.ASCENDING,
                onCheck = {
                    onOrderChange(noteOrder.copy(orderType = OrderType.ASCENDING))
                })
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType == OrderType.DESCENDING,
                onCheck = {
                    onOrderChange(noteOrder.copy(orderType = OrderType.DESCENDING))
                })
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder(),
    onOrderChange: (noteOrder: NoteOrder) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder.sortType == SortType.TITLE,
                onCheck = {
                    onOrderChange(noteOrder.copy(sortType = SortType.TITLE))
                })
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Date",
                selected = noteOrder.sortType == SortType.DATE,
                onCheck = {
                    onOrderChange(noteOrder.copy(sortType = SortType.DATE))
                })
            Spacer(modifier = Modifier.width(8.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Level",
                selected = noteOrder.sortType == SortType.LEVEL,
                onCheck = {
                    onOrderChange(noteOrder.copy(sortType = SortType.LEVEL))
                })
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Reminder",
                selected = noteOrder.sortType == SortType.REMIND_TIME,
                onCheck = {
                    onOrderChange(noteOrder.copy(sortType = SortType.REMIND_TIME))
                })
        }
    }
}