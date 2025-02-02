package com.trackmybus.theKeg.features.schedule.data.local.dao.feedInfo

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.entity.FeedInfoEntity
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.FeedInfo
import io.ktor.util.logging.Logger

class FeedInfoDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : FeedInfoDao {
    override suspend fun getAllFeedInfo(): Result<List<FeedInfoEntity>> =
        runCatching {
            dbFactory.dbQuery {
                FeedInfoEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all feed info", it)
        }

    override suspend fun getFeedInfoById(id: Int): Result<FeedInfo?> =
        runCatching {
            dbFactory.dbQuery {
                FeedInfoEntity.findById(id)?.toModel()
            }
        }.onFailure {
            logger.error("Error getting feed info by id: $id", it)
        }

    override suspend fun addFeedInfo(feedInfo: FeedInfo): Result<FeedInfoEntity> =
        runCatching {
            dbFactory.dbQuery {
                FeedInfoEntity.new {
                    setPropertiesFrom(feedInfo)
                }
            }
        }.onFailure {
            logger.error("Error adding feed info: $feedInfo", it)
        }

    override suspend fun updateFeedInfo(feedInfo: FeedInfo): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingFeedInfo = FeedInfoEntity.findById(feedInfo.id)
                if (existingFeedInfo != null) {
                    existingFeedInfo.apply {
                        setPropertiesFrom(feedInfo)
                    }
                    true
                } else {
                    throw IllegalArgumentException("Feed info with ID ${feedInfo.id} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating feed info: $feedInfo", it)
        }

    override suspend fun deleteFeedInfo(id: Int): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                FeedInfoEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting feed info by id: $id", it)
        }

    private fun FeedInfoEntity.setPropertiesFrom(feedInfo: FeedInfo) {
        this.feedPublisherName = feedInfo.feedPublisherName
        this.feedPublisherUrl = feedInfo.feedPublisherUrl
        this.feedLang = feedInfo.feedLang
        this.feedStartDate = feedInfo.feedStartDate
        this.feedEndDate = feedInfo.feedEndDate
        this.feedVersion = feedInfo.feedVersion
    }
}
