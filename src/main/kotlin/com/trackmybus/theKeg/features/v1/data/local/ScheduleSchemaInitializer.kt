package com.trackmybus.theKeg.features.v1.data.local

import com.trackmybus.theKeg.features.v1.data.local.tables.AgenciesTable
import com.trackmybus.theKeg.features.v1.data.local.tables.CalendarDatesTable
import com.trackmybus.theKeg.features.v1.data.local.tables.CalendarsTable
import com.trackmybus.theKeg.features.v1.data.local.tables.FeedInfoTable
import com.trackmybus.theKeg.features.v1.data.local.tables.RoutesTable
import com.trackmybus.theKeg.features.v1.data.local.tables.ShapesTable
import com.trackmybus.theKeg.features.v1.data.local.tables.StopTimesTable
import com.trackmybus.theKeg.features.v1.data.local.tables.StopsTable
import com.trackmybus.theKeg.features.v1.data.local.tables.TripsTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table

class ScheduleSchemaInitializer {
    private val tablesToInitialize: List<Table> =
        listOf(
            AgenciesTable,
            CalendarsTable,
            CalendarDatesTable,
            FeedInfoTable,
            RoutesTable,
            ShapesTable,
            StopsTable,
            StopTimesTable,
            TripsTable,
        )

    fun initSchemas() {
        tablesToInitialize.forEach { table ->
            SchemaUtils.create(table)
        }
    }
}
