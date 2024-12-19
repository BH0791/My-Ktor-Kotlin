package fr.hamtec.plugins

import fr.hamtec.data.Team
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation(){
    install(RequestValidation){
        validate<Team> { team ->
            if (team.id <= 0) {
                ValidationResult.Invalid("L'identifiant de l'équipe ne doit pas être égal à zéro ou à une valeur négative.")
            }else{
                ValidationResult.Valid
            }
        }
    }
}