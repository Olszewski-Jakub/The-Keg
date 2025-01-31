package com.trackmybus.theKeg.infrastructure.serializers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.serializer
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
class CsvSerializer(val csv: Csv) {
    inline fun <reified T> serialize(records: List<T>): Result<String> {
        return try {
            val serializer = ListSerializer<T>(serializer())
            val result = csv.encodeToString(serializer, records)
            Result.success(result)
        } catch (e: SerializationException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    inline fun <reified T> deserialize(input: String): Result<List<T>> {
        return try {
            val serializer = ListSerializer<T>(serializer())
            val result = csv.decodeFromString(serializer, input)
            Result.success(result)
        } catch (e: SerializationException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    inline fun <reified T> deserializeFile(filePath: String): Result<List<T>> {
        return try {
            val file = File(filePath)
            val input = file.readText().trimIndent()
            deserialize(input)
        } catch (e: SerializationException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}