package fr.hamtec.routes

import fr.hamtec.bd.Teams
import fr.hamtec.dao.TeamDAO
import fr.hamtec.data.Team
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureBaseDonnee() {
    routing {
        get("/ajouter") {
            val insertedTeam: TeamDAO = transaction {
                TeamDAO.new {
                    name = "Laos"
                }
            }
        }
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
                    if(teamList.isNotEmpty()) {
                        call.respond(teamList)
                    } else {
                        log.error("No teams found")
                        call.respond("No teams found")
                    }
                } catch(e: Exception) {
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
//                    val teamList: List<Team> = newSuspendedTransaction {
//                        val allTeams: SizedIterable<TeamDAO> = fetchAllTeamsSafely()
//                        allTeams.map { team ->
//                            Team(team.id.value, team.name)
//                        }
//                    }
                    //*Bonnes Pratiques pour Utiliser SizedIterable<TeamDAO>
                    val teamList: List<Team> = transaction {
                        val allTeams: SizedIterable<TeamDAO> = TeamDAO.all()
                        allTeams.map { team ->
                            Team(team.id.value, team.name)
                        }
                    }
                    // Vérifier et répondre
                    if(teamList.isNotEmpty()) {
                        call.respond(teamList)
                    } else {
                        log.error("No teams found")
                        call.respond("No teams found")
                    }
                } catch(e: Exception) {
                    log.error("Error fetching teams: ${e.message}", e)
                    call.respond("An error occurred while fetching teams")
                }
            }
        }
        get("/bd20") {
            try {
                val teamList: List<Team> = transaction {
                    val allTeams: SizedIterable<TeamDAO> = TeamDAO.all().limit(20, offset = 0)
                    allTeams.map { team ->
                        Team(team.id.value, team.name)
                    }
                }

                // Vérifier et répondre
                call.respond(teamList)

            } catch(e: Exception) {
                log.error("Error fetching teams: ${e.message}", e)
                call.respond("An error occurred while fetching teams")
            }
        }
        get("//teams/{id}") {
            val teamId = call.parameters["id"]?.toIntOrNull()
            if(teamId == null) {
                call.respond(HttpStatusCode.BadRequest, "ID invalide.")
                return@get
            }
            try {
                val team = newSuspendedTransaction {
                    TeamDAO.findById(teamId)
                }

                if(team != null) {
                    call.respond(Team(team.id.value, team.name))
                } else {
                    call.respond(HttpStatusCode.NotFound, "Équipe non trouvée.")
                }
            } catch(e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erreur interne du serveur.")
            }
        }
        //+http://127.0.0.1:8080/findByName?name=France
        get("/findByName") {
            val nameParam = call.request.queryParameters["name"]
            if(nameParam == null) {
                call.respond(HttpStatusCode.BadRequest, "Le paramètre 'name' est manquant.")
                return@get
            }

            val teams = newSuspendedTransaction {
                TeamDAO.find { Teams.name eq nameParam }
                    .map { teamDAO ->
                        Team(id = teamDAO.id.value, name = teamDAO.name)
                    }
            }

            if(teams.isNotEmpty()) {
                call.respond(teams)
            } else {
                call.respond(HttpStatusCode.NotFound, "Aucune équipe trouvée avec le nom '$nameParam'.")
            }
        }
        get("/teams/notInList") {
            /**
             * Explications supplémentaires :
             * Route définie : La route /teams/notInList permet d'accéder à la liste des équipes dont le nom n'est pas "France" ou "Spain".
             *
             * Transaction asynchrone : L'utilisation de newSuspendedTransaction permet d'effectuer la transaction de manière asynchrone, ce qui est adapté pour les applications web basées sur Ktor.
             *
             * Classe Team sérialisable : En annotant la classe Team avec @Serializable, vous facilitez la sérialisation en JSON lors de la réponse HTTP.
             */
            try {
                // Exécution de la transaction de manière asynchrone
                val teams = newSuspendedTransaction {
                    TeamDAO.find { Teams.name notInList listOf("France", "Spain") }
                        .map { teamDAO ->
                            Team(id = teamDAO.id.value, name = teamDAO.name)
                        }
                }

                if (teams.isNotEmpty()) {
                    call.respond(teams)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Aucune équipe correspondante trouvée.")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erreur lors de la récupération des équipes : ${e.message}")
            }
        }
        get("/teams/filter") {
            try {
                /**
                 * Votre requête est correctement écrite pour récupérer les équipes qui ne sont pas "France" ou "Spain"
                 * et dont l'ID est inférieur à 10. En l'intégrant correctement dans un bloc transactionnel et en vous
                 * assurant que toutes les importations et dépendances sont en place, vous devriez être en mesure d'obtenir
                 * les résultats attendus.
                 */
                val teams = newSuspendedTransaction {
                    TeamDAO.find {
                        (Teams.name notInList listOf("France", "Spain")) and
                                (Teams.id less 10)
                    }.map { teamDAO ->
                        Team(id = teamDAO.id.value, name = teamDAO.name)
                    }
                }

                if (teams.isNotEmpty()) {
                    call.respond(teams)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Aucune équipe correspondante trouvée.")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erreur lors de la récupération des équipes : ${e.message}")
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