package com.tools.practicecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    // 以下是cold flow 没有collect就不会执行；相反还有hot flow
    val countDownFlow : Flow<Int> = flow {
        val startingValue = 100
        var curVal = startingValue
        emit(startingValue)
        while (curVal > 0) {
            delay(1000L)
            curVal--
            emit(curVal)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        // 以下写法等同于viewModelScope.launch {countDownFlow.collect}
//        countDownFlow.onEach {time->
//            println("onEach $time")
//        }.launchIn(viewModelScope)
        viewModelScope.launch {
            countDownFlow
                .filter {time->
                    time % 2 == 0
                }
                .map {
                    it.toString()
                }
                .map {
                    Integer.valueOf(it)
                }
                    // 自动取消之前没响应完的，例如println("xx")之前有delay(1500L)
                .collectLatest { time->
                println("cur time is $time")
            }
        }
    }
}