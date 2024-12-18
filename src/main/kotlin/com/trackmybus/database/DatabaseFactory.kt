package com.trackmybus.database

import java.sql.Connection


interface DatabaseFactory {
    fun connect()
    fun close()
    var connection: Connection
}