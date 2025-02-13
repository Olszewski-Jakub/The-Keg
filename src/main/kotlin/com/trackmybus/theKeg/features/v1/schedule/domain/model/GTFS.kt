package com.trackmybus.theKeg.features.v1.schedule.domain.model

data class GTFS(
    val agencies: List<Agency>,
    val calendars: List<Calendar>,
    val calendarDates: List<CalendarDate>,
    val routes: List<Route>,
    val shapes: List<Shape>,
    val stops: List<Stop>,
    val stopTimes: List<StopTime>,
    val trips: List<Trip>,
    val feedInfo: List<FeedInfo>,
)
