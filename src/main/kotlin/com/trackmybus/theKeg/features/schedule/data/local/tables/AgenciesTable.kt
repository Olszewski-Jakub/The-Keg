package com.trackmybus.theKeg.features.schedule.data.local.tables

import org.jetbrains.exposed.dao.id.IdTable

/**
 * Represents the `agencies` table in the database, which contains information about transit agencies.
 */
object AgenciesTable : IdTable<String>("agencies") {
    val agencyName = varchar("agency_name", 255).nullable()
    val agencyUrl = varchar("agency_url", 255).nullable()
    val agencyTimezone = varchar("agency_timezone", 255).nullable()
    override val id = varchar("agency_id",255).entityId()
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id)
}
