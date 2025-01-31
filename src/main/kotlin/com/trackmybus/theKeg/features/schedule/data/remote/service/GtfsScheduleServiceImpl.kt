package com.trackmybus.theKeg.features.schedule.data.remote.service

import com.trackmybus.theKeg.config.AppConfig
import com.trackmybus.theKeg.features.schedule.data.remote.dto.*
import com.trackmybus.theKeg.infrastructure.serializers.CsvSerializer
import io.ktor.util.logging.*
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class GtfsScheduleServiceImpl(
    private val logger: Logger,
    private val csvSerializer: CsvSerializer,
    private val appConfig: AppConfig
) : GtfsScheduleService {
    override suspend fun fetchGtfsData(): Result<GTFSDto> {
        return runCatching {
            downloadGtfsZip()
            extractGtfsZip()
            val gtfsDto = getGtfsData().getOrThrow()
            deleteGtfsData()
            gtfsDto
        }.onFailure { e ->
            logger.error("Failed to fetch GTFS data", e)
        }
    }

    private suspend fun downloadGtfsZip() {
        try {
            val outputDirectory = File(appConfig.gtfsConfig.outputDir)
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs()
            }
            val outputFile = File(outputDirectory, appConfig.gtfsConfig.gtfsFile)
            withTimeout(TIMEOUT) {
                FileOutputStream(outputFile).use { output ->
                    URL(appConfig.gtfsConfig.url).openStream().use { input ->
                        input.copyTo(output)
                    }
                }
            }
            logger.info("GTFS ZIP downloaded to: ${outputFile.absolutePath}")
        } catch (e: Exception) {
            logger.error("Error downloading GTFS ZIP", e)
            throw e
        }
    }

    private suspend fun extractGtfsZip() = withContext(Dispatchers.IO) {
        val extractedFiles = mutableListOf<File>()
        val zipFile = File(appConfig.gtfsConfig.outputDir, appConfig.gtfsConfig.gtfsFile)

        try {
            BufferedInputStream(zipFile.inputStream()).use { bis ->
                ZipInputStream(bis).use { zis ->
                    var entry: ZipEntry? = zis.nextEntry
                    while (entry != null) {
                        val outputFile = File(appConfig.gtfsConfig.outputDir, entry.name)
                        if (entry.isDirectory) {
                            outputFile.mkdirs()
                        } else {
                            try {
                                async {
                                    outputFile.outputStream().use { output ->
                                        zis.copyTo(output)
                                    }
                                    extractedFiles.add(outputFile)
                                }.await()
                            } catch (e: Exception) {
                                logger.error("Error extracting file: ${entry.name}", e)
                            }
                        }
                        zis.closeEntry()
                        entry = zis.nextEntry
                    }
                }
            }
            logger.info("GTFS files extracted to: ${File(appConfig.gtfsConfig.outputDir).absolutePath}")
        } catch (e: Exception) {
            logger.error("Error extracting GTFS ZIP", e)
            throw e
        }
    }

    private suspend fun deleteGtfsData() = withContext(Dispatchers.IO) {
        val outputDirectory = File(appConfig.gtfsConfig.outputDir)
        if (outputDirectory.exists()) {
            try {
                outputDirectory.listFiles()?.map { file ->
                    async {
                        if (file.isDirectory) {
                            file.deleteRecursively()
                        } else {
                            file.delete()
                        }
                    }
                }?.awaitAll()
                logger.info("GTFS data deleted from: ${outputDirectory.absolutePath}")
            } catch (e: Exception) {
                logger.error("Error deleting GTFS data", e)
                throw e
            }
        }
    }

    private suspend fun getGtfsData(): Result<GTFSDto> = coroutineScope {
        try {
//            val agencyDto =
//            val calendarDto =
//            val calendarDateDto =
//            val routeDto =
//            val shapeDto =
//            val stopDto =
//            val stopTimeDto =
//            val tripDto =
//            val feedInfoDto =

            val gtfsData = GTFSDto(
                agencyDto = deserializeFile<AgencyDto>(appConfig.gtfsConfig.agencyFile),
                calendarDto = deserializeFile<CalendarDto>(appConfig.gtfsConfig.calendarFile),
                calendarDatesDto =  deserializeFile<CalendarDateDto>(appConfig.gtfsConfig.calendarDatesFile),
                routesDto = deserializeFile<RouteDto>(appConfig.gtfsConfig.routesFile),
                shapesDto = deserializeFile<ShapeDto>(appConfig.gtfsConfig.shapesFile),
                stopsDto = deserializeFile<StopDto>(appConfig.gtfsConfig.stopsFile),
                stopTimesDto = deserializeFile<StopTimeDto>(appConfig.gtfsConfig.stopTimesFile),
                tripsDto = deserializeFile<TripDto>(appConfig.gtfsConfig.tripsFile),
                feedInfoDto = deserializeFile<FeedInfoDto>(appConfig.gtfsConfig.feedInfoFile)
            )

            logger.info("GTFS data deserialized successfully")
            Result.success(gtfsData)
        } catch (e: Exception) {
            logger.error("Error deserializing GTFS data", e)
            Result.failure(e)
        }
    }

    private inline fun <reified T> deserializeFile(fileName: String): List<T> {
        val file = File(appConfig.gtfsConfig.outputDir, fileName)
        return csvSerializer.deserializeFile<T>(file.absolutePath).getOrElse { e ->
            logger.warn("Failed to deserialize file: $fileName", e)
            emptyList()
        }
    }

    companion object {
        const val TIMEOUT = 30_000L
    }
}