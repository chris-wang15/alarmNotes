package com.tools.practicecompose.feature.presentation.edit_page.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tools.practicecompose.feature.presentation.edit_page.AddEditNoteEvent
import com.tools.practicecompose.feature.presentation.edit_page.AddEditNoteViewModel
import java.util.*


@Composable
fun TimeSelector(
    viewModel: AddEditNoteViewModel,
) {
    val c = Calendar.getInstance()
    val reminderTime = viewModel.noteReminder
    val timeStamp = reminderTime.value
    if (timeStamp != null) {
        c.timeInMillis = timeStamp
    }
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hour = c[Calendar.HOUR_OF_DAY]
    val minute = c[Calendar.MINUTE]
    val mDateText = remember { mutableStateOf("$day - ${month + 1} - $year") }
    val mTimeText = remember { mutableStateOf("$hour : $minute") }
    val calendarState = remember { mutableStateOf(c) }
    val checked = viewModel.noteReminder
    Log.d(TAG, "c.timeInMillis: ${c.timeInMillis}")

    val datePickerDialog = DatePickerDialog(
        LocalContext.current, { _, _year: Int, _month: Int, _day: Int ->
            mDateText.value = "$_day - ${_month + 1} - $_year"
            calendarState.value.set(Calendar.YEAR, _year)
            calendarState.value.set(Calendar.MONTH, _month)
            calendarState.value.set(Calendar.DAY_OF_MONTH, _day)
        }, year, month, day
    )

    val timePickerDialog = TimePickerDialog(
        LocalContext.current, { _, _hour, _minute ->
            mTimeText.value = "$_hour : $_minute"
            calendarState.value.set(Calendar.HOUR, _hour)
            calendarState.value.set(Calendar.MINUTE, _minute)
        }, hour, minute, false
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(onClick = { datePickerDialog.show() }) {
                    Text(
                        text = mDateText.value,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { timePickerDialog.show() }) {
                    Text(
                        text = mTimeText.value,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (reminderTime.value != null) "Enable" else "Disable",
                    style = MaterialTheme.typography.bodyMedium,
                )
                IconToggleButton(
                    checked = reminderTime.value != null,
                    onCheckedChange = {
                        Log.d(TAG, "${calendarState.value.timeInMillis} & ${System.currentTimeMillis()}")
                        viewModel.onEvent(
                            AddEditNoteEvent.ChangeReminderState(
                                calendarState.value.timeInMillis, it
                            )
                        )
                    }
                ) {
                    val tint by animateColorAsState(
                        if (checked.value != null) {
                            Color(0xFFEC407A)
                        } else {
                            Color(0xFFB0BEC5)
                        }
                    )
                    Icon(
                        Icons.Filled.RadioButtonChecked,
                        contentDescription = "Localized description",
                        tint = tint
                    )
                }
            }
        }
    }
}

private const val TAG = "TimeSelector"