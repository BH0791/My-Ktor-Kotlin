package fr.hamtec

import fr.hamtec.routes.configureRoutingFist
import io.ktor.server.application.*

fun Application.configureRouting() {
    configureRoutingFist()
}