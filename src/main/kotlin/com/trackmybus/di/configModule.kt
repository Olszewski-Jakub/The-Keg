package com.trackmybus.di

import com.trackmybus.config.AppConfig
import com.trackmybus.database.DatabaseFactory
import com.trackmybus.database.DatabaseFactoryImpl
import org.koin.dsl.module

val configModule = module {
    single { AppConfig() }
    single<DatabaseFactory> { DatabaseFactoryImpl(get()) }
}