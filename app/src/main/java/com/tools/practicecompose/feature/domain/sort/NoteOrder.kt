package com.tools.practicecompose.feature.domain.sort

data class NoteOrder(
    val sortType: SortType = SortType.TITLE,
    val orderType: OrderType = OrderType.ASCENDING,
)