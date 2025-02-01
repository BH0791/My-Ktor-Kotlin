package fr.hamtec.routes

import fr.hamtec.bd.Players
import fr.hamtec.bd.Teams
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureBaseDonnee() {
    routing {
        get("/bd") {
            log.info("*****- Requête reçue pour /bd")
            try {
                transaction {
                    log.info("*****- Création des tables Teams et Players")
                    SchemaUtils.create(Teams, Players)
                    log.info("*****- Tables créées avec succès, schéma initialiser")
                }
            } catch(e: Exception) {
                log.error("*****- Erreur lors de la configuration de la base de données", e)
                println("Erreur de connexion à la base de données *** : ${e.message}")
            }

        }
    }
}