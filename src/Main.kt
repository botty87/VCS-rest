package com.vcs

import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

const val IS_RELEASE = false

fun main() {
    embeddedServer(
        Netty,
        watchPaths = listOf("VCS-rest"),
        port = 8080,
        module = Application::module
    ).apply { start(wait = true) }
}