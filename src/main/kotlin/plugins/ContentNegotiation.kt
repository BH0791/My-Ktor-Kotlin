package fr.hamtec.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json


//* https://ktor.io/docs/client-serialization.html#register_xml
fun Application.configureContentNegotiation(){
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}