package com.vcs

import io.ktor.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

const val IS_RELEASE = true;

fun main() {
    embeddedServer(
        Netty,
        watchPaths = listOf("VCS-rest"),
        port = 8080,
        module = Application::module
    ).apply { start(wait = true) }
}