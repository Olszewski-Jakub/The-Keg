package com.trackmybus.theKeg.features.v1.schedule.data.local.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

/**
 * Represents the `feed_info` table, which provides metadata about the feed.
 */
object FeedInfoTable : IntIdTable("feed_info") {
    val feedPublisherName = varchar("feed_publisher_name", 255).nullable()
    val feedPublisherUrl = varchar("feed_publisher_url", 255).nullable()
    val feedLang = varchar("feed_lang", 255).nullable()
    val feedStartDate = date("feed_start_date").nullable()
    val feedEndDate = date("feed_end_date").nullable()
    val feedVersion = varchar("feed_version", 255).nullable()
}
