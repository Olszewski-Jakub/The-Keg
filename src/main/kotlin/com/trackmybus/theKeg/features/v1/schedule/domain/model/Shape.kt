package com.trackmybus.theKeg.features.v1.schedule.domain.model

data class Shape(
    val id: Int,
    val shapeId: String,
    val shapePtLat: Double,
    val shapePtLon: Double,
    val shapePtSequence: Int,
    val shapeDistTraveled: Double,
)
