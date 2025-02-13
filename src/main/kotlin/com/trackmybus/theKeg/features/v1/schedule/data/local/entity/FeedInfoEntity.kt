package com.trackmybus.theKeg.features.v1.schedule.data.local.entity

import com.trackmybus.theKeg.features.v1.schedule.data.local.tables.FeedInfoTable
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing feed information in the local database.
 * @param id The ID of the feed information.
 * @property feedPublisherName The name of the feed publisher.
 * @property feedPublisherUrl The URL of the feed publisher.
 * @property feedLang The language of the feed.
 * @property feedStartDate The start date of the feed.
 * @property feedEndDate The end date of the feed.
 * @property feedVersion The version of the feed.
 */
class FeedInfoEntity(
    id: EntityID<Int>,
) : IntEntity(id) {
    companion object : IntEntityClass<FeedInfoEntity>(FeedInfoTable)

    var feedPublisherName: String? by FeedInfoTable.feedPublisherName
    var feedPublisherUrl: String? by FeedInfoTable.feedPublisherUrl
    var feedLang: String? by FeedInfoTable.feedLang
    var feedStartDate: LocalDate? by FeedInfoTable.feedStartDate
    var feedEndDate: LocalDate? by FeedInfoTable.feedEndDate
    var feedVersion: String? by FeedInfoTable.feedVersion
}
