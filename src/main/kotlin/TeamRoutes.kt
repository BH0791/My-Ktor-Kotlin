package fr.hamtec

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.teamRoutes() {
    get("/teams") {
        call.respond(HttpStatusCode.OK)
    }

    get("/teams/{team_id}") {}

    post("/teams") {}

    put("/teams/{team_id}"){}

    delete("/teams/{team_id}"){}

    get("/teams/{team_id}/players"){}

    post("/teams/{team_id}/players"){}

    put("/teams/{team_id}/players/{player_id}"){}

    delete("/teams/{team_id}/players/{player_id}"){}
}