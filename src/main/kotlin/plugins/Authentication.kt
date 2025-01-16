package fr.hamtec.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthentication(){
    install(Authentication){
        basic("auth-basic") {
            realm = "Ktor Server"
            validate {credentials ->
                if(credentials.name == "user" && credentials.password == "password"){
                    UserIdPrincipal(credentials.name)
                }else{
                    null
                }
            }
        }
    }
}