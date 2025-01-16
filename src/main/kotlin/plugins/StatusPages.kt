package fr.hamtec.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

val log = LoggerFactory.getLogger("StatusPages")

fun Application.configureStatusPages(){
    install(StatusPages){
        exception<RequestValidationException>{ call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }
    }
}