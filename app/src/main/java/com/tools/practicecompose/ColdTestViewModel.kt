package com.tools.practicecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Deprecated("for test only")
class ColdTestViewModel : ViewModel() {
    // 以下是cold flow 没有collect就不会执行；相反还有hot flow
    val countDownFlow: Flow<Int> = flow {
        val startingValue = 5
        var curVal = startingValue
        emit(startingValue)
        while (curVal > 0) {
            delay(1000L)
            curVal--
            emit(curVal)
        }
    }

    // stateFlow sharedFlow 是 hotFlow
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<Int>()
    val shareFlow = _shareFlow.asSharedFlow()

    init {
//        collectColdFlow()
        collectHotFlow()
    }

    private fun squareNumber(number: Int) {
        viewModelScope.launch {
            _shareFlow.emit(number * number)
        }
    }

    fun increaseCounter() {
        _stateFlow.value += 1
    }

    private fun collectHotFlow() {
        viewModelScope.launch {
            shareFlow.collect {
                delay(2000L)
                println("FIRST FLOW: received number: $it")
            }
        }
        viewModelScope.launch {
            shareFlow.collect {
                delay(3000L)
                println("SECOND FLOW: received number: $it")
            }
        }
        squareNumber(4)
    }

    private fun collectColdFlow() {
        // 以下写法等同于viewModelScope.launch {countDownFlow.collect}
//        countDownFlow.onEach {time->
//            println("onEach $time")
//        }.launchIn(viewModelScope)

//        viewModelScope.launch {
//            countDownFlow
//                .filter {time->
//                    time % 2 == 0
//                }
//                .map {
//                    it.toString()
//                }
//                .map {
//                    Integer.valueOf(it)
//                }
//                    // 自动取消之前没响应完的，例如println("xx")之前有delay(1500L)
//                .collectLatest { time->
//                println("cur time is $time")
//            }
//        }

        // count 用法
//        viewModelScope.launch {
//            val countVal: Int = countDownFlow
//                .count {
//                    it % 2 == 0
//                }
//            println("countVal is $countVal")
//        }

//        viewModelScope.launch {
//            val reduceResult = countDownFlow.reduce { accumulator: Int, value: Int ->
//                accumulator + value
//            }
//            val foldResult = countDownFlow.fold(100) { accumulator: Int, value: Int ->
//                accumulator + value
//            }
//            println("Results are $reduceResult  and $foldResult")
//        }

        //flatMapConcat and flatMapLatest
//        val flow1 = flow {
//            emit(1)
//            delay(500L)
//            emit(2)
//        }
//        viewModelScope.launch {
//            flow1.flatMapConcat {value-> // also flatMapLatest
//                flow {
//                    emit(value + 1)
//                    delay(500L)
//                    emit(value + 2)
//                }
//            }.collect {
//                println("value is $it")
//            }
//        }

        // 利用cold flow, 在collect 中delay emit
        val flow1 = flow {
            println("FLOW: start delivering Appetizer")
            delay(250L)
            emit("Appetizer")
            println("FLOW: start delivering Main dish")
            delay(1000L)
            emit("Main dish")
            println("FLOW: start delivering Dessert")
            delay(100L)
            emit("Dessert")
        }
        viewModelScope.launch {
            flow1.onEach {
                println("FLOW: $it is delivered")
            }
                // .buffer() // 可以让 collect 不阻塞 emit
                // .conflate() // 可以让来不及 collect 的忽略掉，不阻塞 emit
                .collect {
                    println("FLOW: Now eating $it")
                    delay(2500L)
                    println("FLOW: Finished eating $it")
                }
        }
    }
}