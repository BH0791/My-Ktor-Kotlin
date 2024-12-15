package fr.hamtec
import fr.hamtec.plugins.configureContentNegotiation
import fr.hamtec.plugins.configureRequestValidation
import fr.hamtec.plugins.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =  io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    routing {
        configureContentNegotiation()
        configureRequestValidation()
        configureStatusPages()

        teamRoutes()
    }

}

