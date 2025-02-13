package com.trackmybus.theKeg.features.v1.data.local.manager

import com.trackmybus.theKeg.features.v1.data.remote.dto.DepartureTimeDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.RouteDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopInSequenceDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopOnTripDto
import com.trackmybus.theKeg.features.v1.domain.model.RouteStopInfo
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toLocalTime
import io.ktor.util.logging.Logger
import org.intellij.lang.annotations.Language
import org.jetbrains.exposed.sql.transactions.transaction

class GtfsQueryManagerImpl(
    private val logger: Logger,
) : GtfsQueryManager {
    override suspend fun getStopsForGivenRoute(
        routeId: String,
        firstStopId: String,
        endStopId: String,
    ): Result<List<StopInSequenceDto>> =
        runCatching {
            val routeStops = mutableListOf<StopInSequenceDto>()
            transaction {
                connection
                    .prepareStatement(
                        sql =
                            GET_STOPS_FOR_GIVEN_ROUTE_QUERY
                                .trimIndent()
                                .replace(ROUTE_ID_PARAM, "'$routeId'")
                                .replace(FIRST_STOP_ID_PARAM, "'$firstStopId'")
                                .replace(END_STOP_ID_PARAM, "'$endStopId'"),
                        false,
                    ).executeQuery()
                    .use { rs ->
                        while (rs.next()) {
                            val stopId = rs.getString("stop_id")
                            val stopName = rs.getString("stop_name")
                            val stopLat = rs.getDouble("stop_lat")
                            val stopLon = rs.getDouble("stop_lon")
                            val sequence = rs.getInt("stop_sequence")
                            logger.info("Stop: $stopId, $stopName, $stopLat, $stopLon, $sequence")
                            routeStops.add(StopInSequenceDto(stopId, stopName, stopLat, stopLon, sequence))
                        }
                    }
            }

            routeStops
        }.onFailure {
            logger.error("Error getting stops for route: $routeId", it)
        }

    override suspend fun getDepartureTimesForRouteAndStop(
        date: String,
        routeId: String,
        stopId: String,
    ): Result<List<DepartureTimeDto>> =
        runCatching {
            val departureTimes = mutableListOf<DepartureTimeDto>()
            transaction {
                connection
                    .prepareStatement(
                        sql =
                            DEPARTURE_TIMES_FOR_ROUTE_AND_STOP_QUERY
                                .trimIndent()
                                .replace(DATE_PARAM, "'$date'")
                                .replace(ROUTE_ID_PARAM, "'$routeId'")
                                .replace(STOP_ID_PARAM, "'$stopId'"),
                        false,
                    ).executeQuery()
                    .use { rs ->
                        while (rs.next()) {
                            departureTimes.add(
                                DepartureTimeDto(
                                    departureTime = rs.getString("departure_time").toLocalTime(),
                                    tripId = rs.getString("trip_id"),
                                    directionId = rs.getInt("direction_id"),
                                ),
                            )
                        }
                    }
            }

            departureTimes
        }.onFailure {
            logger.error("Error getting departure times for route $routeId and stop $stopId", it)
        }

    override suspend fun getStopsForTrip(tripId: String): Result<List<StopOnTripDto>> =
        runCatching {
            val stops = mutableListOf<StopOnTripDto>()
            transaction {
                connection
                    .prepareStatement(
                        sql =
                            GET_STOPS_FOR_TRIP_QUERY
                                .trimIndent()
                                .replace(TRIP_ID_PARAM, "'$tripId'"),
                        false,
                    ).executeQuery()
                    .use { rs ->
                        while (rs.next()) {
                            stops.add(
                                StopOnTripDto(
                                    stopSequence = rs.getInt("stop_sequence"),
                                    stopId = rs.getString("stop_id"),
                                    stopName = rs.getString("stop_name"),
                                    departureTime = rs.getString("departure_time").toLocalTime(),
                                    stopLat = rs.getDouble("stop_lat"),
                                    stopLon = rs.getDouble("stop_lon"),
                                ),
                            )
                        }
                    }
            }

            stops
        }.onFailure {
            logger.error("Error getting stops for trip $tripId", it)
        }

    override suspend fun getRoutesPassingThroughStop(stopId: String): Result<List<RouteDto>> =
        runCatching {
            val routes = mutableListOf<RouteDto>()
            transaction {
                connection
                    .prepareStatement(
                        sql =
                            GET_ROUTES_PASSING_THROUGH_STOP_QUERY
                                .trimIndent()
                                .replace(STOP_ID_PARAM, "'$stopId'"),
                        false,
                    ).executeQuery()
                    .use { rs ->
                        while (rs.next()) {
                            routes.add(
                                RouteDto(
                                    routeId = rs.getString("route_id"),
                                    agencyId = rs.getString("agency_id"),
                                    routeShortName = rs.getString("route_short_name"),
                                    routeLongName = rs.getString("route_long_name"),
                                    routeDesc = rs.getString("route_desccription"),
                                    routeType = rs.getInt("route_type"),
                                    routeUrl = rs.getString("route_url"),
                                    routeColor = rs.getString("route_color"),
                                    routeTextColor = rs.getString("route_text_color"),
                                ),
                            )
                        }
                    }
            }

            routes
        }.onFailure {
            logger.error("Error getting routes passing through stop $stopId", it)
        }

    override suspend fun getFirstAndLastStopsForRoute(routeId: String): Result<List<RouteStopInfo>> =
        runCatching {
            val routeStops = mutableListOf<RouteStopInfo>()
            transaction {
                connection
                    .prepareStatement(
                        FIRST_AND_LAST_STOP_FOR_ROUTE_QUERY.trimIndent().replace(ROUTE_ID_PARAM, "'$routeId '"),
                        false,
                    ).executeQuery()
                    .use { rs ->
                        while (rs.next()) {
                            val firstStopId = rs.getString("first_stop_id")
                            val firstStopName = rs.getString("first_stop_name")
                            val lastStopId = rs.getString("last_stop_id")
                            val lastStopName = rs.getString("last_stop_name")
                            val directionId = rs.getInt("direction_id")
                            routeStops.add(RouteStopInfo(firstStopId, firstStopName, lastStopId, lastStopName, directionId))
                        }
                    }
            }

            routeStops
        }.onFailure {
            logger.error("Error getting first and last stops for route: $routeId", it)
        }

    companion object {
        @Language("SQL")
        private const val DEPARTURE_TIMES_FOR_ROUTE_AND_STOP_QUERY =
            """
            SELECT st.departure_time, t.trip_id, t.direction_id
            FROM stop_times st
                     JOIN trips t ON st.trip_id = t.trip_id
                     JOIN routes r ON t.route_id = r.route_id
                     JOIN calendar c ON t.service_id = c.service_id
                     LEFT JOIN calendar_dates cd ON t.service_id = cd.service_id AND cd.date = DATE :date
            WHERE st.stop_id = :stop_id
              AND r.route_id = :route_id
              AND (
                (c.start_date <= DATE :date AND c.end_date >= DATE :date
                    AND CASE
                            WHEN EXTRACT(DOW FROM DATE :date) = 0 THEN c.sunday = TRUE
                            WHEN EXTRACT(DOW FROM DATE :date) = 1 THEN c.monday = TRUE
                            WHEN EXTRACT(DOW FROM DATE :date) = 2 THEN c.tuesday = TRUE
                            WHEN EXTRACT(DOW FROM DATE :date) = 3 THEN c.wednesday = TRUE
                            WHEN EXTRACT(DOW FROM DATE :date) = 4 THEN c.thursday = TRUE
                            WHEN EXTRACT(DOW FROM DATE :date) = 5 THEN c.friday = TRUE
                            WHEN EXTRACT(DOW FROM DATE :date) = 6 THEN c.saturday = TRUE
                     END)
                    OR (cd.date = DATE :date AND cd.exception_type = 1) -- Service added on exception date
                )
              AND (cd.date IS NULL OR cd.exception_type <> 2) -- Exclude service if it was removed
            ORDER BY st.departure_time;
            """

        @Language("SQL")
        private const val GET_STOPS_FOR_TRIP_QUERY =
            """
            SELECT st.stop_sequence, s.stop_id, s.stop_name, st.departure_time, s.stop_lat, s.stop_lon
            FROM stop_times st
                     JOIN stops s ON st.stop_id = s.stop_id
            WHERE st.trip_id = :trip_id
            ORDER BY st.stop_sequence;
            """

        @Language("SQL")
        private const val GET_ROUTES_PASSING_THROUGH_STOP_QUERY =
            """
            SELECT DISTINCT r.route_id,
                r.agency_id,
                r.route_short_name,
                r.route_long_name,
                r.route_desccription,
                r.route_type,
                r.route_url,
                r.route_color,
                r.route_text_color
            FROM stop_times st
                     JOIN trips t ON st.trip_id = t.trip_id
                     JOIN routes r ON t.route_id = r.route_id
            WHERE st.stop_id = :stop_id
            """

        @Language("SQL")
        private const val GET_STOPS_FOR_GIVEN_ROUTE_QUERY =
            """
            WITH SelectedTrip AS (
                -- Find the first trip where the first stop is sequence 1 and the last stop exists
                SELECT st.trip_id
                FROM stop_times st
                JOIN stop_times last_st ON st.trip_id = last_st.trip_id
                JOIN trips t ON st.trip_id = t.trip_id
                WHERE t.route_id = :route_id  -- Ensure the correct route
                  AND st.stop_id = :firstStopId -- First stop
                  AND st.stop_sequence = 1  -- Ensure it's the first stop
                  AND last_st.stop_id = :endStopId  -- Last stop
                  AND last_st.stop_sequence > st.stop_sequence -- Ensure it's later in sequence
                ORDER BY st.trip_id
                LIMIT 1
            ),
            StopBounds AS (
                -- Get the start and end sequence for the selected trip
                SELECT
                    1 AS start_seq, -- Since first stop must always be at sequence 1
                    stop_sequence AS end_seq
                FROM stop_times
                WHERE trip_id = (SELECT trip_id FROM SelectedTrip)
                  AND stop_id = :endStopId -- Last stop
                LIMIT 1
            )
            -- Retrieve all stops between the first and last stop sequence
            SELECT
                st.stop_id,
                st.stop_sequence,
                s.stop_name,
                s.stop_lat,
                s.stop_lon
            FROM stop_times st
            JOIN stops s ON st.stop_id = s.stop_id
            JOIN SelectedTrip t ON st.trip_id = t.trip_id
            WHERE st.stop_sequence BETWEEN 1 AND (SELECT end_seq FROM StopBounds)
            ORDER BY st.stop_sequence
            """

        @Language("SQL")
        private const val FIRST_AND_LAST_STOP_FOR_ROUTE_QUERY =
            """
            WITH FirstAndLastStops AS (
            SELECT
                st.trip_id,
                st.stop_id,
                st.stop_sequence,
                s.stop_name,
                t.direction_id
            FROM stop_times st
            JOIN stops s ON st.stop_id = s.stop_id
            JOIN trips t ON st.trip_id = t.trip_id
            WHERE t.route_id = :route_id  -- Replace '?' with the desired route_id
        ),
        MaxStopSequence AS (
            SELECT trip_id, MAX(stop_sequence) AS max_stop_sequence
            FROM stop_times
            WHERE trip_id IN (
                SELECT trip_id
                FROM trips
                WHERE route_id = :route_id  -- Replace '?' with the desired route_id
            )
            GROUP BY trip_id
        )
        SELECT DISTINCT
            FIRST_VALUE(fs.stop_id) OVER (PARTITION BY fs.trip_id ORDER BY fs.stop_sequence) AS first_stop_id,
            FIRST_VALUE(fs.stop_name) OVER (PARTITION BY fs.trip_id ORDER BY fs.stop_sequence) AS first_stop_name,
            FIRST_VALUE(fs.stop_id) OVER (PARTITION BY fs.trip_id ORDER BY fs.stop_sequence DESC) AS last_stop_id,
            FIRST_VALUE(fs.stop_name) OVER (PARTITION BY fs.trip_id ORDER BY fs.stop_sequence DESC) AS last_stop_name,
            fs.direction_id AS direction_id
        FROM FirstAndLastStops fs
        JOIN MaxStopSequence ms ON fs.trip_id = ms.trip_id
        ORDER BY first_stop_id, last_stop_id;
        """

        private const val DATE_PARAM = ":date"
        private const val ROUTE_ID_PARAM = ":route_id"
        private const val STOP_ID_PARAM = ":stop_id"
        private const val TRIP_ID_PARAM = ":trip_id"
        private const val FIRST_STOP_ID_PARAM = ":firstStopId"
        private const val END_STOP_ID_PARAM = ":endStopId"
    }
}
