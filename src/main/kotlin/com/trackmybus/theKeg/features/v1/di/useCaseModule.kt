@file:Suppress("ktlint:standard:filename")

package com.trackmybus.theKeg.features.v1.di

import com.trackmybus.theKeg.di.getLogger
import com.trackmybus.theKeg.features.v1.domain.usecases.ScheduleUseCase
import com.trackmybus.theKeg.features.v1.domain.usecases.ScheduleUseCaseImp
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
