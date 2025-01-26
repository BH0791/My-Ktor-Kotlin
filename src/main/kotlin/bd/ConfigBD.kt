package fr.hamtec.bd

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

suspend fun configurationBD() {
    withContext(Dispatchers.IO) {
        suspend fun migrateDB() {
            try {
                Flyway
                    .configure()
                    .dataSource("jdbc:postgresql://localhost:5432/teams", "postgres", "Wap74Q+2")
                    .load()
                    .migrate()
                println("Migrations de la nouvelle base de données effectuées avec succès !")
                println("Migrations effectuées avec succès !")
            }catch(e: Exception){
                // Affichage d'erreurs plus informatives
                println("Erreur de connexion à la base de données *** : ${e.message}")
            }
        }
        try {
            migrateDB()

            val config = HikariConfig().apply {
                jdbcUrl = "jdbc:postgresql://localhost:5432/teams"
                driverClassName = "org.postgresql.Driver"
                username = "postgres"
                password = "Wap74Q+2"
            }


            val dataSource = HikariDataSource(config)
            Database.connect(dataSource)
            println("\"Connexion à la base de données effectuée avec succès !")
        }catch(e: Exception){
            println("Erreur de connexion à la base de données *** : ${e.message}")
        }


    }

}