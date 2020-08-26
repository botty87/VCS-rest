package com.vcs

import com.vcs.data.PostResult
import com.vcs.data.json.*
import com.vcs.data.localDB.RetireItem
import com.vcs.di.firebaseModule
import com.vcs.di.controllersModule
import com.vcs.sources.localDB.depots.Depots
import com.vcs.sources.localDB.dictionary.Dictionary
import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.areas.AreasController
import com.vcs.sources.localDB.depots.DepotsController
import com.vcs.sources.localDB.dictionary.DictionaryController
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendar
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainers
import com.vcs.sources.localDB.retires.Retires
import com.vcs.sources.localDB.retires.RetiresController
import com.vcs.sources.localDB.trashContainers.TrashContainers
import com.vcs.sources.localDB.trashContainers.TrashContainersController
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
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
            controllersModule
        )
    }

    val dictionaryController: DictionaryController by inject()
    val depotsController: DepotsController by inject()
    val retiresController: RetiresController by inject()
    val trashController: TrashContainersController by inject()
    val areasController: AreasController by inject()

    routing {
        get("/init") {
            transaction {
                SchemaUtils.create(Dictionary, Depots, Retires, Areas, AreasCalendar, TrashContainers, AreasTrashContainers)
            }
            call.respondText("Tables created")
        }

        get("/dictionary") {
            val dictionary = dictionaryController.getAll().map { DictionaryItemJson(it) }
            call.respond(dictionary)
        }

        get("/depots") {
            val depots = depotsController.getAll().map { DepotItemJson(it) }
            call.respond(depots)
        }

        route("/retires") {
            get() {
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
                        retiresController.delete(retireItemJson.id)
                        call.respond(PostResult.Success<Nothing>())
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
        }

        get("/trash_conts") {
            val trashConts = trashController.getAll().map { TrashContainerJson(it) }
            call.respond(trashConts)
        }

        route("/areas") {
            get() {
                val areas = areasController.getAll().map { AreaItemJson(it) }
                call.respond(areas)
            }

            route("save") {
                post() {
                    try {
                        val areaItemJson = call.receive<AreaItemJson>()
                        val areaItem = areasController.save(areaItemJson)
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