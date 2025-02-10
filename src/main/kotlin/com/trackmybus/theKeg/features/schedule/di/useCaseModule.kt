@file:Suppress("ktlint:standard:filename")

package com.trackmybus.theKeg.features.schedule.di

import com.trackmybus.theKeg.di.getLogger
import com.trackmybus.theKeg.features.schedule.domain.usecases.ScheduleUseCase
import com.trackmybus.theKeg.features.schedule.domain.usecases.ScheduleUseCaseImp
import org.koin.dsl.module

val useCaseModule =
    module {
        single<ScheduleUseCase> {
            ScheduleUseCaseImp(
                getLogger<ScheduleUseCase>(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
    }
