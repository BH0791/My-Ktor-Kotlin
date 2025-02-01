package fr.hamtec.routes

import fr.hamtec.dao.TeamDAO
import fr.hamtec.data.Team
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureBaseDonnee() {
    routing {
        get("/afficheDB") {
            // Envelopper les opérations de base de données dans un bloc runBlocking
            runBlocking {

                // Utiliser withContext pour effectuer des opérations en IO dispatcher
                val allTeams = fetchAllTeams()

                // Vérifier s'il y a des équipes et répondre en conséquence
                if(allTeams.isNotEmpty()) {
                    call.respond(allTeams)
                } else {
                    log.error("No teams found")
                    call.respond("No teams found")
                }
            }
        }
        get("/afficheDBSizedIterable") {
            runBlocking {

                val allTeams = fetchAllTeamsSafely()

                // Loguer les équipes récupérées
                log.info("Teams: $allTeams")

                // Vérifier s'il y a des équipes et répondre en conséquence
//                if (allTeams.any()) {
//                    call.respond(allTeams.map { team ->
//                        Team(team.id.value, team.name)
//                    })
//                } else {
//                    log.error("No teams found")
//                    call.respond("No teams found")
//                }
                // Vérifier s'il y a des équipes et répondre en conséquence
                if(allTeams.any()) {
                    call.respond(allTeams.map { team ->
                        mapOf("id" to team.id.value, "name" to team.name)
                    })
                } else {
                    log.error("No teams found")
                    call.respond("No teams found")
                }
            }
        }
        get("/baseDonnee") {
            runBlocking {
                try {
                    // Utilisation de newSuspendedTransaction pour garantir que toutes les opérations sont effectuées dans un contexte transactionnel sécurisé
                    val allTeams: SizedIterable<TeamDAO> = newSuspendedTransaction {
                        TeamDAO.all()
                    }

                    // Mapping des résultats
                    val teamList = allTeams.map { team ->
                        mapOf("id" to team.id.value, "name" to team.name)
                    }

                    // Vérification et réponse
                    if (teamList.isNotEmpty()) {
                        call.respond(teamList)
                    } else {
                        log.error("No teams found")
                        call.respond("No teams found")
                    }
                } catch (e: Exception) {
                    log.error("Error fetching teams: ${e.message}", e)
                    call.respond("An error occurred while fetching teams")
                }
            }
        }
        get("/baseD") {
            runBlocking {
                try {
                    // Récupérer et mapper les équipes dans un bloc de transaction sécurisé
                    // Utiliser newSuspendedTransaction pour garantir que toutes les opérations sont dans un contexte transactionnel sécurisé
                    val teamList: List<Team> = newSuspendedTransaction {
                        val allTeams: SizedIterable<TeamDAO> = fetchAllTeamsSafely()
                        allTeams.map { team ->
                            Team(team.id.value, team.name)
                        }
                    }

                    // Vérifier et répondre
                    if (teamList.isNotEmpty()) {
                        call.respond(teamList)
                    } else {
                        log.error("No teams found")
                        call.respond("No teams found")
                    }
                } catch (e: Exception) {
                    log.error("Error fetching teams: ${e.message}", e)
                    call.respond("An error occurred while fetching teams")
                }
            }
        }
    }
}

// Méthode pour récupérer toutes les équipes
suspend fun fetchAllTeams(): List<Team> {
    return withContext(Dispatchers.IO) {
        transaction {
            TeamDAO.all().map { team ->
                Team(team.id.value, team.name)
            }
        }
    }
}

// Méthode pour récupérer toutes les équipes
suspend fun fetchAllTeamsSizedIterable(): SizedIterable<TeamDAO> {
    return withContext(Dispatchers.IO) {
        transaction {
            TeamDAO.all()
        }
    }
}

// Méthode pour récupérer toutes les équipes en utilisant SizedIterable de manière sécurisée
suspend fun fetchAllTeamsSafely(): SizedIterable<TeamDAO> {
    return newSuspendedTransaction {
        transaction {
            TeamDAO.all()
        }
    }
}