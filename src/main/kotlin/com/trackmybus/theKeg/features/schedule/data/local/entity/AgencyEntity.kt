package com.trackmybus.theKeg.features.schedule.data.local.entity

import com.trackmybus.theKeg.features.schedule.data.local.tables.AgenciesTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing an agency in the local database.
 * @param id The ID of the agency.
 * @property agencyId The ID of the agency.
 * @property agencyName The name of the agency.
 * @property agencyUrl The URL of the agency.
 * @property agencyTimezone The timezone of the agency.
 */
class AgencyEntity(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, AgencyEntity>(AgenciesTable)

    var agencyId by AgenciesTable.id
    var agencyName by AgenciesTable.agencyName
    var agencyUrl by AgenciesTable.agencyUrl
    var agencyTimezone by AgenciesTable.agencyTimezone
}