package com.trackmybus.theKeg.infrastructure.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggerProvider {
    inline fun <reified T> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)
}
