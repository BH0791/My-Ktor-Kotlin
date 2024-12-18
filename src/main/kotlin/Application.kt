package fr.hamtec
import fr.hamtec.plugins.configureContentNegotiation
import fr.hamtec.plugins.configureRequestValidation
import fr.hamtec.plugins.configureStatusPages
import fr.hamtec.routes.teamRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import java.io.File

fun main(args: Array<String>): Unit =  io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    val logger = LoggerFactory.getLogger("Application")
    routing {
        configureContentNegotiation()
        configureRequestValidation()
        configureStatusPages()
        staticFiles("/static", File("static"))
        teamRoutes()
    }

}

