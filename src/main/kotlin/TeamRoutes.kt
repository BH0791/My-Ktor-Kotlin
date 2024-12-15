package fr.hamtec

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.teamRoutes(){
    get("/teams"){
        call.respond(HttpStatusCode.OK)
    }
}