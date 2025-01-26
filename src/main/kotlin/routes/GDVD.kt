package fr.hamtec.routes

import fr.hamtec.bd.migrateDatabase
import fr.hamtec.data.Team
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.cofigureGDVD() {

    migrateDatabase()

    routing {
        route("/dvds") {
            get {
                transaction {
                    val teams = Team(1, "Italie")
                    println("************>>>>> $teams")

                }
                call.respondText("teams")
            }
        }
        post {
            val team = call.receive<Team>()
//                transaction {
//                    Team.insert {
//                        it[id] = team.id
//                        it[title] = team.title
//                    }
//                }
//                call.respond(HttpStatusCode.Created, "DVD created")
        }
    }
}

