package dicionarioinclusivo

import database.initDB
import io.ktor.server.application.*
import dicionarioinclusivo.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    initDB()

    configureRouting()
    configureSerialization()
    configureHTTP()
}