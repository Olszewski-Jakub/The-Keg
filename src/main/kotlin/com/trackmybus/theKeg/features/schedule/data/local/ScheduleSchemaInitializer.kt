package com.trackmybus.theKeg.features.schedule.data.local

import com.trackmybus.theKeg.features.schedule.data.local.tables.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table

class ScheduleSchemaInitializer {
    private val tablesToInitialize: List<Table> = listOf(
        AgenciesTable,
        CalendarsTable,
        CalendarDatesTable,
        FeedInfoTable,
        RoutesTable,
        ShapesTable,
        StopsTable,
        StopTimesTable,
        TripsTable
    )

    fun initSchemas() {
        tablesToInitialize.forEach { table ->
            SchemaUtils.create(table)
        }
    }
}