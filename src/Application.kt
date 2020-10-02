package com.vcs

import com.vcs.data.PostResult
import com.vcs.data.json.*
import com.vcs.di.controllersModule
import com.vcs.sources.localDB.depots.Depots
import com.vcs.sources.localDB.dictionary.Dictionary
import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.areas.AreasController
import com.vcs.sources.localDB.depots.DepotsController
import com.vcs.sources.localDB.dictionary.DictionaryController
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendar
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainers
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainersController
import com.vcs.sources.localDB.referencedTables.areasTrashContainersNew.AreasTrashContainersControllerNew
import com.vcs.sources.localDB.retires.Retires
import com.vcs.sources.localDB.retires.RetiresController
import com.vcs.sources.localDB.trashContainers.TrashContainers
import com.vcs.sources.localDB.trashContainers.TrashContainersController
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import org.koin.ktor.ext.inject

fun Application.module() {
    initDB()
    install(DefaultHeaders)
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        header(HttpHeaders.AccessControlAllowOrigin)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        allowCredentials = true
        anyHost()
    }
    install(CallLogging)
    install(ContentNegotiation) {
        gson {}
    }

    startKoin {
        logger(PrintLogger(Level.NONE))
        modules(
            controllersModule
        )
    }

    val dictionaryController: DictionaryController by inject()
    val depotsController: DepotsController by inject()
    val retiresController: RetiresController by inject()
    val trashController: TrashContainersController by inject()
    val areasController: AreasController by inject()
    val areaTrashContainersController: AreasTrashContainersController by inject()
    val areaTrashContainersControllerNew: AreasTrashContainersControllerNew by inject()

    routing {
        get("/init") {
            transaction {
                SchemaUtils.create(Dictionary, Depots, Retires, Areas, AreasCalendar, TrashContainers, AreasTrashContainers)
            }
            call.respondText("Tables created")
        }

        route("/dictionary") {
            get() {
                val dictionary = dictionaryController.getAll().map { DictionaryItemJson(it) }
                call.respond(dictionary)
            }

            route("new") {
                post {
                    try {
                        val dictionaryItemJson = call.receive<DictionaryItemJson>()
                        val dictionaryItem = dictionaryController.createNew(dictionaryItemJson)
                        call.respond(PostResult.Success(DictionaryItemJson(dictionaryItem)))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("update") {
                post{
                    try {
                        val dictionaryItemJson = call.receive<DictionaryItemJson>()
                        val dictionaryItem = dictionaryController.update(dictionaryItemJson)
                        call.respond(PostResult.Success(DictionaryItemJson(dictionaryItem)))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("delete") {
                post{
                    try {
                        val dictionaryItemJson = call.receive<DictionaryItemJson>()
                        dictionaryController.delete(dictionaryItemJson)
                        call.respond(PostResult.Success<Nothing>())
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
        }

        route("/depots") {
            get {
                val depots = depotsController.getAll().map { DepotItemJson(it) }
                call.respond(depots)
            }

            post("update") {
                try {
                    val depotItemJson = call.receive<DepotItemJson>()
                    val depotItem = depotsController.update(depotItemJson)
                    call.respond(PostResult.Success(DepotItemJson(depotItem)))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("new") {
                try {
                    val depotItemJson = call.receive<DepotItemJson>()
                    val depotItem = depotsController.createNew(depotItemJson)
                    call.respond(PostResult.Success(DepotItemJson(depotItem)))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("delete") {
                try {
                    val depotItemJson = call.receive<DepotItemJson>()
                    depotsController.delete(depotItemJson)
                    call.respond(PostResult.Success<Nothing>())
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }
        }

        route("/retires") {
            get {
                val retires = retiresController.getAll().map { RetireItemJson(it) }
                call.respond(retires)
            }

            route("new") {
                post {
                    try {
                        val retireItemJson = call.receive<RetireItemJson>()
                        val retireItem = retiresController.createNew(retireItemJson)
                        call.respond(PostResult.Success(RetireItemJson(retireItem)))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("update") {
                post {
                    try {
                        val retireItemJson = call.receive<RetireItemJson>()
                        val retireItem = retiresController.update(retireItemJson)
                        call.respond(PostResult.Success(RetireItemJson(retireItem)))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("delete") {
                post {
                    try {
                        val retireItemJson = call.receive<RetireItemJson>()
                        retiresController.delete(retireItemJson)
                        call.respond(PostResult.Success<Nothing>())
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
        }

        route("/trash_conts") {
            get {
                val trashConts = trashController.getAll().map { TrashContainerJson(it) }
                call.respond(trashConts)
            }

            get("areas/{id}") {
                val areaId = call.parameters["id"]
                val result = areaTrashContainersController.getTrashContainersForArea()
                call.respond(areaId ?: "null")
            }

            get("move") {
                areaTrashContainersControllerNew.move()
                call.respond("ok")
            }
        }

        route("/areas") {
            get {
                val areas = areasController.getAll().map { AreaItemJson(it) }
                call.respond(areas)
            }

            route("update") {
                post {
                    try {
                        val areaItemJson = call.receive<AreaItemJson>()
                        val areaItem = areasController.update(areaItemJson)
                        call.respond(PostResult.Success(AreaItemJson(areaItem)))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
        }
    }
}

private fun initDB() {
    val config = HikariConfig("/hikari.properties")
    val ds = HikariDataSource(config)
    Database.connect(ds)
}