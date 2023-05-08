package com.tools.practicecompose.feature.presentation.level

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tools.practicecompose.feature.domain.model.NoteLevel
import com.tools.practicecompose.feature.domain.model.defaultLevelColorMap
import com.tools.practicecompose.feature.domain.model.defaultLevelKeyList
import com.tools.practicecompose.feature.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(
    private val noteUseCase: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(defaultLevelColorMap)
    val state: State<Map<Int, NoteLevel>> = _state
    private val _levelKeyList = mutableStateOf(defaultLevelKeyList)
    val levelKeyList: State<List<Int>> = _levelKeyList

    private val _editLevel: MutableState<NoteLevel?> = mutableStateOf(null)
    val editLevel: State<NoteLevel?> = _editLevel

    private var getColorMapJob: Job? = null

    init {
        loadLevelMap()
    }

    fun onEvent(event: EditLevelEvent) {
        when (event) {
            is EditLevelEvent.EditLevelAndDismissDialog -> {
                if (event.value != state.value[event.value.level]) {
                    viewModelScope.launch {
                        noteUseCase.editLevelUseCase.invoke(event.value)
                    }
                }
                _editLevel.value = null
            }
            is EditLevelEvent.ShowColorPickerDialog -> {
                _editLevel.value = event.value
            }
        }
    }

    private fun loadLevelMap() {
        getColorMapJob?.cancel()
        getColorMapJob = noteUseCase.getLevelColorUseCase.invoke().onEach {
            _state.value = it
        }
            .launchIn(viewModelScope)
    }
}