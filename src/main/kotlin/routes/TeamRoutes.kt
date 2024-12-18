package fr.hamtec.routes

import fr.hamtec.data.Team
import io.ktor.http.*
import io.ktor.server.html.*
import kotlinx.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.teamRoutes() {
    get("/teams") {
        call.respond(HttpStatusCode.OK)
    }

    get("/teams/{team_id}") {
        val teamId: Int? = call.parameters["team_id"]?.toIntOrNull()
        val language: String? = call.request.headers["Accept-Language"]
        if (teamId == null) {
            call.respond(HttpStatusCode.BadRequest)
        } else {
            val team = Team(
                id = teamId,
                name = "France"
            )
            call.respondText(language.toString() + team)
            call.respond(HttpStatusCode.OK, team)
        }
    }

    post("/teams") {
        val team = call.receive<Team>()
        call.respond(HttpStatusCode.OK, team.name)
    }

    put("/teams/{team_id}") {}

    delete("/teams/{team_id}") {}

    get("/teams/{team_id}/players") {}

    post("/teams/{team_id}/players") {}

    put("/teams/{team_id}/players/{player_id}") {}

    delete("/teams/{team_id}/players/{player_id}") {}
//- Ne fait pas parti de l'application, c'est juste pour la compréhension -



    get("/retry") {
        call.response.header(
            "X-Retry-Count", 10
        )
        call.respondText(
            "Header set with X-Retry-Count"
        )
    }
    get("/hello/{user_name}"){
        val userName = call.parameters["user_name"]
        call.respondHtml {
            body{
                p{
                    +"Hello $userName"
                }
            }
        }
    }
    get("/web/teams"){
        val teams = listOf(
            Team(1, "Paris"),
            Team(2, "Lyon"),
            Team(3, "Nantes"),
            Team(4, "Metz"),
        )
        call.respondHtml(HttpStatusCode.OK){
            head {
                link(rel = "stylesheet", href = "/static/style.css", type = "text/css")
                title {
                    +"${teams.size} Teams"
                }
            }
            body {
                table("my-table centered") {
                    attributes["border"] = "1"
                    thead {
                        tr {
                            th {+"id"}
                            th {+"name"}
                        }
                    }
                    tbody {
                        teams.forEach { team ->
                            tr {
                                td {+"${team.id}"}
                                td {+team.name}
                            }
                        }
                    }
                }
            }
        }
    }
}