package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing shape data.
 *
 * The `ShapesDto` class is used to transfer data about the path that a vehicle travels along a route alignment.
 * This data is typically sourced from the `shapes.txt` file.
 *
 * @property shapeId The unique identifier for the shape, which serves as a lookup reference to `shape_id` in `trips.txt`.
 * @property shapePtLat The latitude of a shape point.
 * @property shapePtLon The longitude of a shape point.
 * @property shapePtSequence The sequence in which the shape points connect to form the shape.
 * @property shapeDistTraveled The distance traveled along the shape from the first shape point.
 */
@Serializable
data class ShapeDto(
    @SerialName("shape_id") val shapeId: String? = null,
    @SerialName("shape_pt_lat") val shapePtLat: Double? = null,
    @SerialName("shape_pt_lon") val shapePtLon: Double? = null,
    @SerialName("shape_pt_sequence") val shapePtSequence: Int? = null,
    @SerialName("shape_dist_traveled") val shapeDistTraveled: Double? = null
)