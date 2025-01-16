package fr.hamtec

import fr.hamtec.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import org.slf4j.LoggerFactory

//Logger
val log = LoggerFactory.getLogger(Application::class.java)

//+ Point d'entrée de l'application
//+ La fonction main de l'application peut simplement appeler la fonction main du moteur HTTP choisi.
fun main(args: Array<String>) {
    EngineMain.main(args)
}


fun Application.module() {
    val jwtSecret: String = environment.config.property("ktor.jwt.secret").getString()
    log.info("JWT Secret: $jwtSecret")
    println("********>>>> $jwtSecret")
    configureContentNegotiation()
    configureRequestValidation()
    configureStatusPages()
    configureTemplating()
    configureAuthentication()
    configureRouting()
}

