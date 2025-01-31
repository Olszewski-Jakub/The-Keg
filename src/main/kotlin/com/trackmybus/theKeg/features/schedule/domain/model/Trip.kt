package com.trackmybus.theKeg.features.schedule.domain.model

data class Trip(
     val routeId: String,
     val serviceId: String,
     val tripId: String,
     val tripHeadsign: String,
     val tripShortName: String,
     val directionId: Int,
     val blockId: String,
     val shapeId: String
)
