@file:Suppress("ktlint:standard:filename")

package com.trackmybus.theKeg.features.v1.di

import org.koin.core.module.Module

val scheduleModules =
    listOf<Module>(
        daoModule,
        serviceModules,
        repositoryModule,
        scheduleDatabaseModule,
        useCaseModule,
    )
