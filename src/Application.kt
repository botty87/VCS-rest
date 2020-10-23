package com.vcs

import com.vcs.data.http.PostResult
import com.vcs.data.json.*
import com.vcs.di.controllersModule
import controllers.areas.AreasController
import controllers.depots.DepotsController
import controllers.dictionary.DictionaryController
import controllers.referencedTables.areasTrashContainers.AreasTrashContainersController
import controllers.retires.RetiresController
import com.vcs.controllers.trashContainers.TrashContainersController
import com.vcs.controllers.users.UsersController
import com.vcs.data.dbTables.*
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
    val usersController: UsersController by inject()

    routing {
        get("/init") {
            transaction {
                SchemaUtils.create(Dictionary, Depots, Retires, Areas, AreasCalendar, TrashContainers, AreasTrashContainers)
            }
            call.respondText("Tables created")
        }

        route("/dictionary") {
            get {
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
                try {
                    val areaId = call.parameters["id"]!!.toInt()
                    val trashContainers = areaTrashContainersController.getTrashContainersForArea(areaId)
                            .map { TrashContainerJson(it) }
                    call.respond(trashContainers)
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            get("cont/{id}") {
                try {
                    val containerId = call.parameters["id"]!!.toInt()
                    val areasId = areaTrashContainersController.getAreasIdForTrashContainer(containerId)
                    call.respond(areasId)
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("update") {
                try {
                    val trashContainerAreasJson = call.receive<TrashContainerAreasJson>()
                    val trashContainerItem = trashController.update(trashContainerAreasJson.trashContainer)
                    areaTrashContainersController.setTrashContainerAndAreas(trashContainerItem, trashContainerAreasJson.areasId)
                    call.respond(PostResult.Success(TrashContainerJson(trashContainerItem)))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("new") {
                try {
                    val trashContainerAreasJson = call.receive<TrashContainerAreasJson>()
                    val trashContainerItem = trashController.new(trashContainerAreasJson.trashContainer)
                    areaTrashContainersController.setTrashContainerAndAreas(trashContainerItem, trashContainerAreasJson.areasId)
                    call.respond(PostResult.Success(TrashContainerJson(trashContainerItem)))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("delete") {
                try {
                    val trashContainerJson = call.receive<TrashContainerJson>()
                    trashController.delete(trashContainerJson)
                    call.respond(PostResult.Success<Nothing>())
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
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

        route("/user") {
            post("login") {
                try {
                    val userJson = call.receive<UserItemJson>()
                    val token = usersController.login(userJson)
                    call.respond(PostResult.Success(token))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }
        }
    }
}

private fun initDB() {
    val config = HikariConfig("/hikari.properties")
    val ds = HikariDataSource(config)
    Database.connect(ds)
    transaction {
        SchemaUtils.create(Areas, Depots, Dictionary, AreasCalendar, AreasTrashContainers, Retires, TrashContainers, Users, Tokens)
    }
}