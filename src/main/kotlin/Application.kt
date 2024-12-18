package fr.hamtec

import fr.hamtec.plugins.configurecontentNegotiation
import fr.hamtec.routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*

//+ Point d'entrée de l'application
//+ La fonction main de l'application peut simplement appeler la fonction main du moteur HTTP choisi.
fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configurecontentNegotiation()
    configureRouting()
}

