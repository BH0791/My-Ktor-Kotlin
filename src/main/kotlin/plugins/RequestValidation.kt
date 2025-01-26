package fr.hamtec.plugins

import fr.hamtec.data.Team
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation(){
    //* inline fun <T : Any> validate(noinline block: suspend (T) -> ValidationResult)
    install(RequestValidation){
        validate<Team> {team ->
            if(team.id <= 0) {
                ValidationResult.Invalid("The team ID should not zero or a negative value")
            }else{
                ValidationResult.Valid
            }
        }
    }
}