package fr.hamtec

import fr.hamtec.routes.configureRoutingSecond
import io.ktor.server.application.*

fun Application.configureRouting() {
//    configureRoutingFist()
    configureRoutingSecond()
}