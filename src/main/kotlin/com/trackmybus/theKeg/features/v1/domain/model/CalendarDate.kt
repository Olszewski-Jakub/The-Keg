package com.trackmybus.theKeg.features.v1.domain.model

import kotlinx.datetime.LocalDate

data class CalendarDate(
    val id: Int,
    val serviceId: String,
    val date: LocalDate,
    val exceptionType: ScheduleAdjustmentType,
)
