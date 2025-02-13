package com.trackmybus.theKeg.features.v1.data.local.dao.feedInfo

import com.trackmybus.theKeg.features.v1.data.local.entity.FeedInfoEntity
import com.trackmybus.theKeg.features.v1.domain.model.FeedInfo

/**
 * Data Access Object (DAO) interface for managing feed information data in the local database.
 */
interface FeedInfoDao {
    /**
     * Retrieves all feed information from the local database.
     * @return A [Result] containing a list of [FeedInfoEntity] objects.
     */
    suspend fun getAllFeedInfo(): Result<List<FeedInfoEntity>>

    /**
     * Retrieves feed information by its ID.
     * @param id The ID of the feed information to retrieve.
     * @return A [Result] containing the [FeedInfoEntity] object if found, or null if not found.
     */
    suspend fun getFeedInfoById(id: Int): Result<FeedInfo?>

    /**
     * Adds new feed information to the local database.
     * @param feedInfo The [FeedInfoEntity] object to add.
     * @return A [Result] containing the newly added [FeedInfoEntity] object.
     */
    suspend fun addFeedInfo(feedInfo: FeedInfo): Result<FeedInfoEntity>

    /**
     * Updates existing feed information in the local database.
     * @param feedInfo The [FeedInfoEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateFeedInfo(feedInfo: FeedInfo): Result<Boolean>

    /**
     * Deletes feed information from the local database by its ID.
     * @param id The ID of the feed information to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteFeedInfo(id: Int): Result<Boolean>
}
