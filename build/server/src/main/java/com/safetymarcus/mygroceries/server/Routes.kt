package com.safetymarcus.mygroceries.server

import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.apiRoutes() {
    log.info("Installing routes")
//    install(Resources)
//    install(ContentNegotiation) {
//        json(Json {
//            prettyPrint = true
//            isLenient = true
//        })
//    }
    routing {
        get("/") {
            call.application.environment.log.info("Routing through base path /")
            call.respondText { "Hello world!" }
        }
        get("/items") {
            call.application.environment.log.info("Routing through /items")
            val items = ItemsRepository.getItems()
            call.respond("Found ${items.count()} items")
        }
    }
}