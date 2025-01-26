package fr.hamtec

import fr.hamtec.bd.configurationBD
import fr.hamtec.bd.migrateDatabase
import fr.hamtec.plugins.*
import fr.hamtec.routes.configureBaseDonnee
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import io.ktor.server.cio.*
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

val logHeader = LoggerFactory.getLogger("Application-Header-perso")
val jwtSecret: String by lazy {
    val dotenv = Dotenv.load()
    dotenv["JWT_SECRET"] ?: "default_secret"
}
//+ Point d'entrée de l'application
//+ La fonction main de l'application peut simplement appeler la fonction main du moteur HTTP choisi.
fun main(args: Array<String>) {
    EngineMain.main(args)
}


fun Application.module() {
    //logHeader.info("JWT Secret: $jwtSecret")
    runBlocking {
        configurationBD()
    }

    configureContentNegotiation()
    configureRequestValidation()
    configureStatusPage()
    configureAuthentification()
    configureBaseDonnee()
}

