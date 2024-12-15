package fr.hamtec

import fr.hamtec.data.Team
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.teamRoutes() {
    get("/teams") {
        call.respond(HttpStatusCode.OK)
    }

    get("/teams/{team_id}") {
        val teamId: Int? = call.parameters["team_id"]?.toInt()

        if (teamId == null){
            call.respond(HttpStatusCode.BadRequest)
        }else{
            val team = Team(
                id = teamId,
                name = "France"
            )
            call.respond(HttpStatusCode.OK, team)
        }
    }

    post("/teams") {
        val team= call.receive<Team>()
        call.respond(HttpStatusCode.OK, team.name)
    }

    put("/teams/{team_id}"){}

    delete("/teams/{team_id}"){}

    get("/teams/{team_id}/players"){}

    post("/teams/{team_id}/players"){}

    put("/teams/{team_id}/players/{player_id}"){}

    delete("/teams/{team_id}/players/{player_id}"){}
}