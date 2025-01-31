package com.trackmybus.theKeg.features.schedule.di

import org.koin.core.module.Module

val scheduleModules = listOf<Module>(
    daoModule,
    serviceModules,
    repositoryModule,
    scheduleDatabaseModule
)