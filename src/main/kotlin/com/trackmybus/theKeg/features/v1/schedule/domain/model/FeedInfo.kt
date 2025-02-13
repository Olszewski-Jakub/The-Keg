package com.trackmybus.theKeg.features.v1.schedule.domain.model

import kotlinx.datetime.LocalDate

data class FeedInfo(
    val id: Int,
    val feedPublisherName: String,
    val feedPublisherUrl: String,
    val feedLang: String,
    val feedStartDate: LocalDate,
    val feedEndDate: LocalDate,
    val feedVersion: String,
)
