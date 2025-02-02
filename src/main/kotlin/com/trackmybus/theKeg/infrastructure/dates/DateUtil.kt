package com.trackmybus.theKeg.infrastructure.dates

import kotlinx.datetime.*

object DateUtil {
    /**
     * Convert a UTC date-time string to a local date-time in the specified timezone.
     *
     * @param utcDateTime ISO string of the UTC time
     * @param targetTimeZone Timezone to convert to
     * @return Converted local date-time as string
     */
    fun convertUTCToLocal(
        utcDateTime: String,
        targetTimeZone: String,
        pattern: String = "yyyy-MM-dd HH:mm:ss",
    ): String {
        val utcInstant = Instant.parse(utcDateTime)
        val zoneId = TimeZone.of(targetTimeZone)
        val localDateTime = utcInstant.toLocalDateTime(zoneId)
        return formatDateTime(localDateTime, pattern)
    }

    /**
     * Convert a local date-time string in a specific timezone to UTC.
     *
     * @param localDateTime Local date-time string
     * @param sourceTimeZone Timezone of the local date-time
     * @param pattern Pattern of the provided date-time
     * @return UTC date-time as string
     */
    fun convertLocalToUTC(
        localDateTime: String,
        sourceTimeZone: String,
        pattern: String = "yyyy-MM-dd HH:mm:ss",
    ): String {
        val zoneId = TimeZone.of(sourceTimeZone)
        val localDateTimeObj = LocalDateTime.parse(localDateTime)
        val utcInstant = localDateTimeObj.toInstant(zoneId)
        return utcInstant.toString() // ISO format
    }

    /**
     * Get the current UTC date-time.
     *
     * @param pattern Optional pattern for formatting the date-time
     * @return Current UTC date-time as string
     */
    fun getCurrentUTCDateTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        val utcNow = Clock.System.now()
        val localDateTime = utcNow.toLocalDateTime(TimeZone.UTC)
        return formatDateTime(localDateTime, pattern)
    }

    /**
     * Get the current date-time in a specific timezone.
     *
     * @param targetTimeZone Timezone to get the current date-time in
     * @param pattern Optional pattern for formatting the date-time
     * @return Current date-time in the specified timezone as string
     */
    fun getCurrentDateTimeInTimeZone(
        targetTimeZone: String,
        pattern: String = "yyyy-MM-dd HH:mm:ss",
    ): String {
        val zoneId = TimeZone.of(targetTimeZone)
        val currentDateTime = Clock.System.now().toLocalDateTime(zoneId)
        return formatDateTime(currentDateTime, pattern)
    }

    /**
     * Extension function to convert an Int in the format YYYYmmDD to a LocalDate object.
     *
     * @return LocalDate representing the date
     * @throws IllegalArgumentException if the Int does not represent a valid date
     */
    fun Int.toDateObject(): LocalDate {
        val dateString = this.toString()
        require(dateString.length == 8) { "Date must be in the format YYYYmmDD" }

        val year = dateString.substring(0, 4).toInt()
        val month = dateString.substring(4, 6).toInt()
        val day = dateString.substring(6, 8).toInt()

        return LocalDate(year, month, day)
    }

    /**
     * Extension function to convert a LocalDate object to an Int in the format YYYYmmDD.
     *
     * @return Int representing the date in YYYYmmDD format
     */
    fun LocalDate.toIntDate(): Int = this.year * 10000 + this.monthNumber * 100 + this.dayOfMonth

    /**
     * Format the LocalDateTime into a string using the provided pattern.
     */
    private fun formatDateTime(
        localDateTime: LocalDateTime,
        pattern: String,
    ): String {
        return localDateTime.toString() // You can create a custom formatting extension if required.
    }

    fun String.toLocalTime(): LocalTime {
        require(this.matches(Regex("\\d{2}:\\d{2}:\\d{2}"))) {
            "Invalid time format. Expected format is HH:mm:ss"
        }

        val (hour, minute, second) = this.split(":").map { it.toInt() }
        require(hour in 0..24 && minute in 0..59 && second in 0..59) {
            "Invalid time values. Hour must be between 0 and 24, minute and second must be between 0 and 59"
        }

        val adjustedHour = if (hour == 24) 0 else hour
        return LocalTime(adjustedHour, minute, second)
    }

    fun LocalTime.toFormattedString(): String = "%02d:%02d:%02d".format(this.hour, this.minute, this.second)
}
