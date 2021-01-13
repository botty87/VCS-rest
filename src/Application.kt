package com.vcs

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.vcs.controllers.advices.AdvicesController
import com.vcs.controllers.mobileAppVersion.MobileAppVersionController
import com.vcs.controllers.trashContainers.TrashContainersController
import com.vcs.controllers.users.UsersController
import com.vcs.data.dbTables.*
import com.vcs.data.http.LoginRequest
import com.vcs.data.http.PostRequest
import com.vcs.data.http.PostResult
import com.vcs.data.json.toJson
import com.vcs.data.json.userItems.toJson
import com.vcs.di.controllersModule
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import controllers.areas.AreasController
import controllers.depots.DepotsController
import controllers.dictionary.DictionaryController
import controllers.referencedTables.areasTrashContainers.AreasTrashContainersController
import controllers.retires.RetiresController
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
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
        jackson {
            // extension method of ObjectMapper to allow config etc
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModule(JavaTimeModule())
        }
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
    val advicesController: AdvicesController by inject()
    val mobileAppVersionController: MobileAppVersionController by inject()

    routing {
        get("/init") {
            transaction {
                SchemaUtils.create(Dictionary, Depots, Retires, Areas, AreasCalendar, TrashContainers, AreasTrashContainers)
            }
            call.respondText("Tables created")
        }

        route("/dictionary") {
            get {
                val dictionary = dictionaryController.getAll().map { it.toJson() }
                call.respond(dictionary)
            }

            route("new") {
                post {
                    try {
                        val request = call.receive<PostRequest.DictionaryItemJson>()
                        val dictionaryItem = dictionaryController.createNew(request.data)
                        call.respond(PostResult.Success(dictionaryItem.toJson()))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("update") {
                post{
                    try {
                        val request = call.receive<PostRequest.DictionaryItemJson>()
                        val dictionaryItem = dictionaryController.update(request.data)
                        call.respond(PostResult.Success(dictionaryItem.toJson()))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("delete") {
                post{
                    try {
                        val request = call.receive<PostRequest.DictionaryItemJson>()
                        dictionaryController.delete(request.data)
                        call.respond(PostResult.Success<Nothing>())
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
        }

        route("/depots") {
            get {
                val depots = depotsController.getAll().map { it.toJson() }
                call.respond(depots)
            }

            post("update") {
                try {
                    val request = call.receive<PostRequest.DepotItemJson>()
                    val depotItem = depotsController.update(request.data)
                    call.respond(PostResult.Success(depotItem.toJson()))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("new") {
                try {
                    val request = call.receive<PostRequest.DepotItemJson>()
                    val depotItem = depotsController.createNew(request.data)
                    call.respond(PostResult.Success(depotItem.toJson()))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("delete") {
                try {
                    val request = call.receive<PostRequest.DepotItemJson>()
                    depotsController.delete(request.data)
                    call.respond(PostResult.Success<Nothing>())
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }
        }

        route("/retires") {
            get {
                val retires = retiresController.getAll().map { it.toJson()}
                call.respond(retires)
            }

            route("new") {
                post {
                    try {
                        val request = call.receive<PostRequest.RetireItemJson>()
                        val retireItem = retiresController.createNew(request.data)
                        call.respond(PostResult.Success(retireItem.toJson()))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("update") {
                post {
                    try {
                        val request = call.receive<PostRequest.RetireItemJson>()
                        val retireItem = retiresController.update(request.data)
                        call.respond(PostResult.Success(retireItem.toJson()))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }

            route("delete") {
                post {
                    try {
                        val request = call.receive<PostRequest.RetireItemJson>()
                        retiresController.delete(request.data)
                        call.respond(PostResult.Success<Nothing>())
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
        }

        route("/trash_conts") {
            get {
                val trashConts = trashController.getAll().map { it.toJson() }
                call.respond(trashConts)
            }

            get("areas/{id}") {
                try {
                    val areaId = call.parameters["id"]!!.toInt()
                    val trashContainers = areaTrashContainersController.getTrashContainersForArea(areaId)
                            .map { it.toJson() }
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
                    val request = call.receive<PostRequest.TrashContainerAreasJson>()
                    val trashContainerItem = trashController.update(request.data.trashContainer)
                    areaTrashContainersController.setTrashContainerAndAreas(trashContainerItem, request.data.areasId)
                    call.respond(PostResult.Success(trashContainerItem.toJson()))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("new") {
                try {
                    val request = call.receive<PostRequest.TrashContainerAreasJson>()
                    val trashContainerItem = trashController.new(request.data.trashContainer)
                    areaTrashContainersController.setTrashContainerAndAreas(trashContainerItem, request.data.areasId)
                    call.respond(PostResult.Success(trashContainerItem.toJson()))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("delete") {
                try {
                    val request = call.receive<PostRequest.TrashContainerJson>()
                    trashController.delete(request.data)
                    call.respond(PostResult.Success<Nothing>())
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }
        }

        route("/areas") {
            get {
                val areas = areasController.getAll().map { it.toJson() }
                call.respond(areas)
            }

            post("update") {
                try {
                    val request = call.receive<PostRequest.AreaItemJson>()
                    val areaItem = areasController.update(request.data)
                    call.respond(PostResult.Success(areaItem.toJson()))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }
        }

        route("/advices") {
            get {
                val advices = advicesController.getAll().map { it.toJson() }
                call.respond(advices)
            }

            post("update") {
                try {
                    val request = call.receive<PostRequest.AdviceItemJson>()
                    val adviceItem = advicesController.update(request.data)
                    call.respond(PostResult.Success(adviceItem.toJson()))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("new") {
                try {
                    val request = call.receive<PostRequest.AdviceItemJson>()
                    val adviceItem = advicesController.createNew(request.data)
                    call.respond(PostResult.Success(adviceItem.toJson()))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }

            post("delete") {
                try {
                    val request = call.receive<PostRequest.AdviceItemJson>()
                    advicesController.delete(request.data)
                    call.respond(PostResult.Success<Nothing>())
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }
        }

        route("/user") {
            post("login") {
                try {
                    val loginRequest = call.receive<LoginRequest>()
                    val loginResponse = usersController.login(loginRequest)
                    call.respond(PostResult.Success(loginResponse))
                } catch (e: Throwable) {
                    call.respond(PostResult.Error(e.localizedMessage))
                }
            }
        }

        get("/mobAppVer/{iOSVersionNeeded}") {
            val iOSVersionNeeded = call.parameters["iOSVersionNeeded"].toBoolean()
            call.respond(mobileAppVersionController.get(iOSVersionNeeded).toJson())
        }

        route("/admin") {
            route("users") {
                post("list") {
                    try {
                        val request = call.receive<PostRequest.NoDataAdmin>()
                        val users = usersController.getUsers(request.token)
                        call.respond(PostResult.Success(users.map { it.toJson() }))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
                post("add_edit") {
                    try {
                        val userRequest = call.receive<PostRequest.UserItemJson>()
                        val user = usersController.addEdit(userRequest.data)
                        call.respond(PostResult.Success(user.toJson()))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
                post("change_pwd") {
                    try {
                        val userPass = call.receive<PostRequest.SetPassword>()
                        usersController.setPassword(userPass.data)
                        call.respond(PostResult.Success<Nothing>())
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
                post("delete") {
                    try {
                        val userRequest = call.receive<PostRequest.UserItemJson>()
                        usersController.delete(userRequest.data)
                        call.respond(PostResult.Success<Nothing>())
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
            route("mobAppVer") {
                post("set") {
                    try {
                        val mobAppVerRequest = call.receive<PostRequest.MobileAppVersionJson>()
                        val mobAppVer = mobileAppVersionController.set(mobAppVerRequest.data)
                        call.respond(PostResult.Success(mobAppVer.toJson()))
                    } catch (e: Throwable) {
                        call.respond(PostResult.Error(e.localizedMessage))
                    }
                }
            }
        }

        route("user") {
            post("change_pwd") {
                try {
                    val changePasswordRequest = call.receive<PostRequest.ChangePassword>()
                    usersController.changePassword(changePasswordRequest.token, changePasswordRequest.data)
                    call.respond(PostResult.Success<Nothing>())
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
    Database.connect(ds).useNestedTransactions = true
    transaction {
        SchemaUtils.createMissingTablesAndColumns(Areas, Depots, Dictionary, AreasCalendar, AreasTrashContainers,
                Retires, TrashContainers, Users, Tokens, Advices, AdvicesAreas, MobileAppVersion)
    }
}