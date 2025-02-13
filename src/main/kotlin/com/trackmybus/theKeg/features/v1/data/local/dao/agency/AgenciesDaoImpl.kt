package com.trackmybus.theKeg.features.v1.data.local.dao.agency

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.data.local.entity.AgencyEntity
import com.trackmybus.theKeg.features.v1.data.local.tables.AgenciesTable
import com.trackmybus.theKeg.features.v1.domain.model.Agency
import io.ktor.util.logging.Logger
import org.jetbrains.exposed.dao.id.EntityID

class AgenciesDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : AgenciesDao {
    override suspend fun getAllAgencies(): Result<List<AgencyEntity>> =
        runCatching {
            dbFactory.dbQuery {
                AgencyEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all agencies", it)
        }

    override suspend fun getAgencyById(id: String): Result<AgencyEntity?> =
        runCatching {
            dbFactory.dbQuery {
                AgencyEntity.findById(id)
            }
        }.onFailure {
            logger.error("Error getting agency by id: $id", it)
        }

    override suspend fun addAgency(agency: Agency): Result<AgencyEntity> =
        runCatching {
            dbFactory.dbQuery {
                AgencyEntity.new {
                    setProperties(agency.agencyId, agency.agencyName, agency.agencyUrl, agency.agencyTimezone)
                }
            }
        }.onFailure {
            logger.error("Error adding agency: ${agency.agencyName}, ${agency.agencyUrl}, ${agency.agencyTimezone}", it)
        }

    override suspend fun updateAgency(agency: Agency): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingAgency = AgencyEntity.findById(agency.agencyId)
                if (existingAgency != null) {
                    existingAgency.setProperties(agency.agencyId, agency.agencyName, agency.agencyUrl, agency.agencyTimezone)
                    true
                } else {
                    throw IllegalArgumentException("Agency with ID ${agency.agencyId} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating agency: $agency", it)
        }

    override suspend fun deleteAgency(id: String): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                AgencyEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting agency by id: $id", it)
        }

    private fun AgencyEntity.setProperties(
        agencyId: String,
        agencyName: String?,
        agencyUrl: String?,
        agencyTimezone: String?,
    ) {
        this.agencyId = EntityID<String>(agencyId, AgenciesTable)
        this.agencyName = agencyName
        this.agencyUrl = agencyUrl
        this.agencyTimezone = agencyTimezone
    }
}
