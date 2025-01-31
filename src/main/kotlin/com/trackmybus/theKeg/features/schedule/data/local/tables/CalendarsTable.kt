package com.trackmybus.theKeg.features.schedule.data.local.tables

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

/**
 * Represents the `calendar` table, which stores service availability information.
 */
object CalendarsTable : IdTable<String>("calendar") {
    val serviceId = varchar("service_id",255).entityId()
    val monday = bool("monday").nullable()
    val tuesday = bool("tuesday").nullable()
    val wednesday = bool("wednesday").nullable()
    val thursday = bool("thursday").nullable()
    val friday = bool("friday").nullable()
    val saturday = bool("saturday").nullable()
    val sunday = bool("sunday").nullable()
    val startDate = date("start_date").nullable()
    val endDate = date("end_date").nullable()
    override val id = serviceId
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id)
}