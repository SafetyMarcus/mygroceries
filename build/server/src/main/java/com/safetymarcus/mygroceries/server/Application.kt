package com.safetymarcus.mygroceries.server

import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.config.ApplicationConfigurationException
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8081,
        module = { module(args) },
    ).start(wait = true)
}

fun Application.module(args: Array<String>) {
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