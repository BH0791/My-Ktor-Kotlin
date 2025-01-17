package fr.hamtec.routes

import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.routing.*

fun Application.configureOpenAPI(){
    routing {
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
//        openAPI(path = "openapi", swaggerFile = "openapi/documentation.json")
        }
    }