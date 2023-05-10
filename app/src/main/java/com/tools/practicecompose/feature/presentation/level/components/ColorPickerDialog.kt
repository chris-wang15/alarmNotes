package com.tools.practicecompose.feature.presentation.level.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tools.practicecompose.feature.domain.model.selectableColorMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    initId: Int = 0,
    onDismiss: (selectedColorId: Int) -> Unit
) {
    val selectedId = remember { mutableStateOf(initId) }
    Dialog(onDismissRequest = { onDismiss(selectedId.value) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.95f),
            shape = RoundedCornerShape(8.dp),
        ) {
            ColorPickerContent(selectedId, onDismiss)
        }
    }
}

@Composable
private fun ColorPickerContent(
    selectedId: MutableState<Int>,
    onDismiss: (selectedColorId: Int) -> Unit
) {

    val colorIdList: List<Int> = ArrayList(selectableColorMap.keys)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(selectableColorMap[selectedId.value]!!))
            )

            Text(text = "Selected")

            IconButton(
                onClick = { onDismiss(selectedId.value) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Exit Color Picker"
                )
            }
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        thickness = 1.dp
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(colorIdList) { colorId ->
            Box(modifier = Modifier
                .aspectRatio(1f) // LazyVerticalGrid can not use size, image will be deformed
                .shadow(15.dp, CircleShape)
                .clip(CircleShape)
                .background(Color(selectableColorMap[colorId]!!))
                .border(
                    width = 3.dp,
                    color = if (selectedId.value == colorId) {
                        Color.Black
                    } else Color.Transparent,
                    shape = CircleShape,
                )
                .clickable {
                    selectedId.value = colorId
                }
            )
        }
    }
}