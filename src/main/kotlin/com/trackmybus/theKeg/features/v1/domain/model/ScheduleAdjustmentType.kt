package com.trackmybus.theKeg.features.v1.domain.model

enum class ScheduleAdjustmentType {
    SERVICE_ADDED,
    SERVICE_REMOVED,
    ;

    companion object {
        fun fromInt(value: Int): ScheduleAdjustmentType =
            when (value) {
                1 -> SERVICE_ADDED
                2 -> SERVICE_REMOVED
                else -> throw IllegalArgumentException("Invalid value for ScheduleAdjustmentType")
            }

        fun toInt(value: ScheduleAdjustmentType): Int =
            when (value) {
                SERVICE_ADDED -> 1
                SERVICE_REMOVED -> 2
            }
    }
}
