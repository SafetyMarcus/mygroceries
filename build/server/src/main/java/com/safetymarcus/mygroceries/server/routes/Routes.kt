package com.safetymarcus.mygroceries.server.routes

import com.safetymarcus.mygroceries.server.routes.items.ItemsRepository
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun Application.apiRoutes() {
    log.info("Installing routes")
//    install(Resources)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    routing {
        get("/") {
            call.application.environment.log.info("Routing through base path /")
            call.respondText { "Hello world!" }
        }
        get("/items") {
            call.application.environment.log.info("Routing through /items")
            val items = ItemsRepository.getItems()
            call.respond(items)
        }
    }
}