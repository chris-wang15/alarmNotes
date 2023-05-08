package com.tools.practicecompose.feature.presentation.edit_page.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tools.practicecompose.feature.presentation.edit_page.AddEditNoteEvent
import com.tools.practicecompose.feature.presentation.edit_page.AddEditNoteViewModel
import com.tools.practicecompose.feature.presentation.notes.components.DefaultRadioButton
import java.util.*


@Composable
fun TimeSelector(
    viewModel: AddEditNoteViewModel,
) {
    val reminderState = viewModel.noteReminder
    val c = Calendar.getInstance()
    reminderState.value?.let {
        c.timeInMillis = it
    }
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hour = c[Calendar.HOUR_OF_DAY]
    val minute = c[Calendar.MINUTE]
    val calendarState = remember { mutableStateOf(c) }
    Log.d(TAG, "c.timeInMillis: ${c.timeInMillis}")

    val datePickerDialog = DatePickerDialog(
        LocalContext.current, { _, _year: Int, _month: Int, _day: Int ->
            calendarState.value.set(Calendar.YEAR, _year)
            calendarState.value.set(Calendar.MONTH, _month)
            calendarState.value.set(Calendar.DAY_OF_MONTH, _day)
            viewModel.onEvent(
                AddEditNoteEvent.ChangeReminderState(
                    calendarState.value.timeInMillis, true
                )
            )
        }, year, month, day
    )

    val timePickerDialog = TimePickerDialog(
        LocalContext.current, { _, _hour, _minute ->
            calendarState.value.set(Calendar.HOUR, _hour)
            calendarState.value.set(Calendar.MINUTE, _minute)
            viewModel.onEvent(
                AddEditNoteEvent.ChangeReminderState(
                    calendarState.value.timeInMillis, true
                )
            )
        }, hour, minute, true
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = 1.dp, MaterialTheme.colorScheme.primary)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = { datePickerDialog.show() }) {
                Text(
                    text = if (reminderState.value == null) {
                        "Select Date"
                    } else {
                        val yearText = calendarState.value.get(Calendar.YEAR)
                        val monthText = calendarState.value.get(Calendar.MONTH) + 1
                        val dayText = calendarState.value.get(Calendar.DAY_OF_MONTH)
                        "$dayText - $monthText - $yearText"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = { timePickerDialog.show() }) {
                Text(
                    text = if (reminderState.value == null) {
                        "Select Time"
                    } else {
                        val minuteText = calendarState.value.get(Calendar.MINUTE)
                        val hourText = calendarState.value.get(Calendar.HOUR_OF_DAY)
                        "$hourText : $minuteText"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        DefaultRadioButton(
            text = if (reminderState.value != null) "Enable" else "Disable",
            selected = reminderState.value != null,
            onCheck = {
                if (reminderState.value != null) {
                    calendarState.value = Calendar.getInstance()
                    viewModel.onEvent(
                        AddEditNoteEvent.ChangeReminderState(
                            calendarState.value.timeInMillis, false
                        )
                    )
                }
            })
    }
}

private const val TAG = "TimeSelector"