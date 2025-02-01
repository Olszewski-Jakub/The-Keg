package com.trackmybus.theKeg.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.intellij.lang.annotations.Language
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class BaseRepository(
    private val connection: Connection,
) {
    suspend fun <T> query(
        @Language("SQL") sql: String,
        params: List<Any?> = emptyList(),
        mapper: (ResultSet) -> T,
    ): List<T> =
        withContext(Dispatchers.IO) {
            connection.prepareStatement(sql).use { statement ->
                setParameters(statement, params)
                statement.executeQuery().use { resultSet ->
                    val results = mutableListOf<T>()
                    while (resultSet.next()) {
                        results.add(mapper(resultSet))
                    }
                    results
                }
            }
        }

    suspend fun update(
        @Language("SQL") sql: String,
        params: List<Any?> = emptyList(),
    ): Int =
        withContext(Dispatchers.IO) {
            connection.prepareStatement(sql).use { statement ->
                // Suppress warning
                setParameters(statement, params)
                statement.executeUpdate()
            }
        }

    private fun setParameters(
        statement: PreparedStatement,
        params: List<Any?>,
    ) {
        params.forEachIndexed { index, param ->
            when (param) {
                is String -> statement.setString(index + 1, param)
                is Int -> statement.setInt(index + 1, param)
                is Long -> statement.setLong(index + 1, param)
                is Boolean -> statement.setBoolean(index + 1, param)
                null -> statement.setObject(index + 1, null)
                else -> throw IllegalArgumentException("Unsupported parameter type: ${param::class}")
            }
        }
    }
}
