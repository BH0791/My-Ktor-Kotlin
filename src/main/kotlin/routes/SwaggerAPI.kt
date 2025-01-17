package fr.hamtec.routes

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureSwaggerUI(){
    routing {
        swaggerUI(path = "swagger-ui", swaggerFile = "openapi/documentation.json")
//        swaggerUI(path = "swagger-ui", swaggerFile = "openapi/documentation.yaml")
    }
}