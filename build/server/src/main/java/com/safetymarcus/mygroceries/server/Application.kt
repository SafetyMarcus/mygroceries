package com.safetymarcus.mygroceries.server

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.config.ApplicationConfigurationException
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.cors.routing.CORS
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8081,
        module = { module(args) },
    ).start(wait = true)
}

fun Application.module(args: Array<String>) {
    install(CORS) {
        allowHost("localhost:8082")
        allowHeader(io.ktor.http.HttpHeaders.ContentType)
        allowMethod(io.ktor.http.HttpMethod.Get)
        allowMethod(io.ktor.http.HttpMethod.Post)
        allowMethod(io.ktor.http.HttpMethod.Put)
        allowMethod(io.ktor.http.HttpMethod.Delete)
        allowCredentials = true // If you need to handle cookies or authorization headers
        maxAgeInSeconds = 3600 // Optional: how long the preflight request can be cached
    }
    apiRoutes()
    configureDatabases(args)
}

fun Application.configureDatabases(args: Array<String>) {
    log.info("Configuring database\n${System.getProperties()}")
    val props = args.associate { it.split('=').let { it[0] to it[1] } }
    Database.connect( //Set up your own postgres db and use the url and user credentials in the command line arguments
        url = props["url"] ?: throw ApplicationConfigurationException("Missing database url"),
        user = props["user"] ?: throw ApplicationConfigurationException("Missing database user"),
        password = props["password"] ?: throw ApplicationConfigurationException("Missing database password"),
    )
}