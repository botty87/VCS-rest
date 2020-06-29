package com.vcs

import com.vcs.data.json.*
import com.vcs.data.localDB.TrashContainerItem
import com.vcs.di.firebaseModule
import com.vcs.di.controllersModule
import com.vcs.di.toolsModule
import com.vcs.sources.localDB.depots.Depots
import com.vcs.sources.localDB.dictionary.Dictionary
import sources.localDB.dictionary.DictionaryController
import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.areas.AreasController
import com.vcs.sources.localDB.depots.DepotsController
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendar
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainers
import com.vcs.sources.localDB.retires.Retires
import com.vcs.sources.localDB.retires.RetiresController
import com.vcs.sources.localDB.trashContainers.TrashContainers
import com.vcs.sources.localDB.trashContainers.TrashContainersController
import com.vcs.tools.DBMigration
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger
import org.koin.ktor.ext.inject

fun Application.module() {
    initDB()
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {}
    }

    startKoin {
        logger(PrintLogger())
        modules(
            firebaseModule,
            controllersModule,
            toolsModule
        )
    }

    val dictionaryController: DictionaryController by inject()
    val depotsController: DepotsController by inject()
    val retiresController: RetiresController by inject()
    val trashController: TrashContainersController by inject()
    val areasController: AreasController by inject()
    val dbMigration: DBMigration by inject()

    routing {
        get("/update_firestore") {
            call.respondText(dbMigration.fromFirestoreToLocal())
        }

        get("/dictionary") {
            val dictionary = dictionaryController.getAll().map { DictionaryItemJson(it) }
            call.respond(dictionary)
        }

        get("/depots") {
            val depots = depotsController.getAll().map { DepotItemJson(it) }
            call.respond(depots)
        }

        get("/retires") {
            val retires = retiresController.getAll().map { RetireItemJson(it) }
            call.respond(retires)
        }

        get("/trash_conts") {
            val trashConts = trashController.getAll().map { TrashContainerJson(it) }
            call.respond(trashConts)
        }

        get("/areas") {
            val areas = areasController.getAll().map { AreaItemJson(it) }
            call.respond(areas)
        }
    }
}

private fun initDB() {
    val config = HikariConfig("/hikari.properties")
    val ds = HikariDataSource(config)
    Database.connect(ds)

    transaction {
        SchemaUtils.create(Dictionary, Depots, Retires, Areas, AreasCalendar, TrashContainers, AreasTrashContainers)
    }
}