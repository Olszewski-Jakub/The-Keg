package com.trackmybus.theKeg.di

import com.trackmybus.theKeg.infrastructure.serializers.CsvSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val csvModule =
    module {
        single {
            Csv {
                hasHeaderRecord = true
                ignoreUnknownColumns = true
                delimiter = ','
                hasTrailingDelimiter = true
            }
        }

        single { CsvSerializer(get()) }
    }
