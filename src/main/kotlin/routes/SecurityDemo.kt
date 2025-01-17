package fr.hamtec.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fr.hamtec.LogUtil
import fr.hamtec.data.UserCredentials
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import java.util.*

val log = LoggerFactory.getLogger("Routing")

fun Application.configureRoutingSecond() {
    routing {
        authentication {
            post("/lolo"){
                LogUtil.info("Requête POST reçue à /login")
                val userCredentials = call.receive<UserCredentials>()
                if(userCredentials.username == "kotlin" && userCredentials.password == "book"){
                    val token = JWT.create()
                        .withClaim("username", userCredentials.username)
                        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                        .sign(Algorithm.HMAC256("jwtSecret"))
                    call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
                }else{
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }


}