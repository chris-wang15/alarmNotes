package com.tools.practicecompose.feature.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tools.practicecompose.feature.domain.model.Note
import java.util.*

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    color: Color,
    cornerRadius: Dp = 10.dp,
    onDeleteClick: () -> Unit,
) {
    val remindStr = note.remindTime?.let {
        val c = Calendar.getInstance()
        c.timeInMillis = it
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        "$day - ${month + 1} & $hour : $minute"
    }


    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawRoundRect(
                color = color,
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            // Title
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Content
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        // close button
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = { onDeleteClick() }
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                imageVector = Icons.Default.Close,
                contentDescription = "Delete Note"
            )
        }

        // reminder time
        if (!remindStr.isNullOrBlank()) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                text = remindStr,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )
        }
    }
}