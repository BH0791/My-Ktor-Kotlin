package fr.hamtec.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fr.hamtec.jwtSecret
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

//fun Application.configureAuthentication(){
//    install(Authentication){
//        basic("auth-basic") {
//            realm = "Ktor Server"
//            validate {credentials ->
//                if(credentials.name == "user" && credentials.password == "password"){
//                    UserIdPrincipal(credentials.name)
//                }else{
//                    null
//                }
//            }
//        }
//    }
//}
fun Application.configureAuthentification() {
    install(Authentication) {
        basic("auth-basic") {
            validate { credentials ->
                if(credentials.name == "user" && credentials.password == "password") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
        jwt("auth-jwt") {
            realm = "Ktor Server"
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .build()
            )
            validate { credential ->
                if(credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
