package fr.hamtec.plugins

import fr.hamtec.data.Team
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation(){
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ResquestValidation
    install(RequestValidation){
        validate<Team> { team ->
            if (team.id <= 0) {
                ValidationResult.Invalid("The team ID should not be zero or a negative value")
            }else{
                ValidationResult.Valid
            }
        }
    }
}