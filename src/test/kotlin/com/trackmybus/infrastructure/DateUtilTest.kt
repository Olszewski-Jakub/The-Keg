package com.trackmybus.infrastructure

import com.trackmybus.theKeg.infrastructure.dates.DateUtil
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toDateObject
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toFormattedString
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toIntDate
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toLocalTime
import junit.framework.TestCase.assertTrue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.junit.Test
import kotlin.test.assertEquals

class DateUtilTest {
    @Test
    fun `convert UTC to local time`() {
        val utcDateTime = "2025-02-13T12:00:00Z"
        val targetTimeZone = "America/New_York"
        val result = DateUtil.convertUTCToLocal(utcDateTime, targetTimeZone)
        assertEquals("2025-02-13T07:00", result)
    }

    @Test
    fun `convert local time to UTC`() {
        val localDateTime = "2025-02-13T07:00:00"
        val sourceTimeZone = "America/New_York"
        val result = DateUtil.convertLocalToUTC(localDateTime, sourceTimeZone)
        assertEquals("2025-02-13T12:00:00Z", result)
    }

    @Test
    fun `get current UTC date-time`() {
        val result = DateUtil.getCurrentUTCDateTime()
        assertTrue(result.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?(Z|[+-]\\d{2}:\\d{2})?")))
    }

    @Test
    fun `get current date-time in timezone`() {
        val targetTimeZone = "America/New_York"
        val result = DateUtil.getCurrentDateTimeInTimeZone(targetTimeZone)
        assertTrue(result.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?(Z|[+-]\\d{2}:\\d{2})?")))
    }

    @Test
    fun `convert Int to LocalDate`() {
        val dateInt = 20250213
        val result = dateInt.toDateObject()
        assertEquals(2025, result.year)
        assertEquals(Month.FEBRUARY, result.month)
        assertEquals(13, result.dayOfMonth)
    }

    @Test
    fun `convert LocalDate to Int`() {
        val localDate = LocalDate(2025, 2, 13)
        val result = localDate.toIntDate()
        assertEquals(20250213, result)
    }

    @Test
    fun `convert valid string to LocalTime`() {
        val timeString = "12:34:56"
        val result = timeString.toLocalTime()
        assertEquals(12, result.hour)
        assertEquals(34, result.minute)
        assertEquals(56, result.second)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `convert invalid string to LocalTime`() {
        val timeString = "25:00:00"
        timeString.toLocalTime()
    }

    @Test
    fun `convert LocalTime to formatted string`() {
        val localTime = LocalTime(12, 34, 56)
        val result = localTime.toFormattedString()
        assertEquals("12:34:56", result)
    }
}
