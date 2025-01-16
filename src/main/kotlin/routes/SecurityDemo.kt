package fr.hamtec.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutingSecond() {
    routing {
        get("/hello/{user_name}") {

        }
    }
}