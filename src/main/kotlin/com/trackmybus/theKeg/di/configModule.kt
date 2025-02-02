package com.trackmybus.theKeg.di

import com.trackmybus.theKeg.config.AppConfig
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.database.DatabaseFactoryImpl
import org.koin.dsl.module

val configModule =
    module {
        single { AppConfig() }
        single<DatabaseFactory> { DatabaseFactoryImpl(getLogger<DatabaseFactoryImpl>(), get(), get()) }
        single { get<DatabaseFactory>().database }
    }
