package com.trackmybus.infrastructure

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.infrastructure.serializers.CsvSerializer
import kotlinx.serialization.csv.Csv
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CsvSerializerTest {
    private val csv = Csv { }
    private val csvSerializer = CsvSerializer(csv)

    fun setUp() {
        configureKoinUnitTest()
    }

    @Test
    fun serialize_validRecords_returnsSerializedString() {
        val records = listOf("record1,record2", "record3,record4")
        val result = csvSerializer.serialize(records)
        assertTrue(result.isSuccess)
        assertEquals("\"record1,record2\"\n\"record3,record4\"", result.getOrNull())
    }

    @Test
    fun serialize_emptyRecords_returnsEmptyString() {
        val records = emptyList<String>()
        val result = csvSerializer.serialize(records)
        assertTrue(result.isSuccess)
        assertEquals("", result.getOrNull())
    }

    @Test
    fun serialize_invalidRecords_returnsFailure() {
        val records = listOf(Any())
        val result = csvSerializer.serialize(records)
        assertTrue(result.isFailure)
    }

    @Test
    fun deserialize_validString_returnsRecords() {
        val input = "record1,record2"
        val result = csvSerializer.deserialize<String>(input)
        assertTrue(result.isSuccess)
        assertEquals(listOf("record1", "record2"), result.getOrNull())
    }

    @Test
    fun deserialize_emptyString_returnsEmptyList() {
        val input = ""
        val result = csvSerializer.deserialize<String>(input)
        assertTrue(result.isSuccess)
        assertEquals(emptyList<String>(), result.getOrNull())
    }

    @Test
    fun deserialize_invalidString_returnsFailure() {
        val input = "invalid"
        val result = csvSerializer.deserialize<Int>(input)
        assertTrue(result.isFailure)
    }

    @Test
    fun deserializeFile_invalidFile_returnsFailure() {
        val filePath = "src/test/resources/invalid.csv"
        val result = csvSerializer.deserializeFile<String>(filePath)
        assertTrue(result.isFailure)
    }

    @Test
    fun deserializeFile_nonExistentFile_returnsFailure() {
        val filePath = "src/test/resources/nonexistent.csv"
        val result = csvSerializer.deserializeFile<String>(filePath)
        assertTrue(result.isFailure)
    }
}
