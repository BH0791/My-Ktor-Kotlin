package fr.hamtec.routes

import fr.hamtec.data.Team
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("\n\nApplication")
    routing {
        staticResources("/static", "static")
        post("/teams") {
            val team = call.receive<Team>()
            logger.info("Équipe reçue: ${team.name}")
            call.respond(HttpStatusCode.OK, team.name)
        }
//        get("/teams/{team_id}"){
//            val teamId: Int? = call.parameters["team_id"]?.toIntOrNull()
//            logger.info("Équipe reçue: $teamId")
//            if (teamId == null){
//                call.respond(HttpStatusCode.BadRequest)
//            }else{
//                val team = Team(
//                    id = teamId,
//                    name = "A voir!"
//                )
//                call.respond(HttpStatusCode.OK, team)
//            }
//        }

        get("/hello/{user_name}") {
            val username = call.parameters["user_name"]
            call.respondHtml(HttpStatusCode.OK) {
                body {
                    p {
                        id = "intro-paragraph"
                        text("Hello ${username}")
                    }
                }
            }
        }
        get("/web/teams") {
            val teams = listOf(
                Team(1, "Paris"),
                Team(2, "Brest"),
                Team(3, "Sète")
            )
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title { text("${teams.size} Teams") }
                    link(rel = "stylesheet", href = "/static/style.css", type = "text/css")
                }
                body {
                    table("my-table-centered") {
                        //attributes["border"] = "1"
                        thead {
                            tr {
                                th { text("***** id *****") }
                                th { text("names") }
                            }
                        }
                        tbody {
                            teams.forEach { team ->
                                tr {
                                    th { text("${team.id}") }
                                    th { text("${team.name}") }
                                }
                            }
                        }
                    }
                }
            }
        }
        get("/index") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }
        get("/web/info") {
            call.respondHtml {
                body {
                    h1 { +"Heading 1" }
                    p {
                        id = "intro-paragraph"
                        classes = setOf("intro")
                        style = "color: red; font-size: 16px;"
                        +"Voici une démonstration de kotlinx.html."
                    }
                    div(classes = "container left tree") {
                        a("https://kotlinlang.org") {
                            target = ATarget.blank
                            +"Main site"
                        }
                    }
                }
            }
        }
    }
}