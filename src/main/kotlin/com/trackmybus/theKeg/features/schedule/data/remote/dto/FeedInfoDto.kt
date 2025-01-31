package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing feed information data.
 *
 * The `FeedInfoDto` class is used to transfer data about the feed publisher.
 * This data is typically sourced from the `feed_info.txt` file.
 *
 * @property feedPublisherName The name of the feed publisher.
 * @property feedPublisherUrl The URL of the feed publisher's website.
 * @property feedLang The language of the feed.
 * @property feedStartDate The start date of the feed in YYYYMMDD format.
 * @property feedEndDate The end date of the feed in YYYYMMDD format.
 * @property feedVersion The version of the feed.
 */
@Serializable
data class FeedInfoDto(
    @SerialName("feed_publisher_name") val feedPublisherName: String? = null,
    @SerialName("feed_publisher_url") val feedPublisherUrl: String? = null,
    @SerialName("feed_lang") val feedLang: String? = null,
    @SerialName("feed_start_date") val feedStartDate: Int? = null,
    @SerialName("feed_end_date") val feedEndDate: Int? = null,
    @SerialName("feed_version") val feedVersion: String? = null
)