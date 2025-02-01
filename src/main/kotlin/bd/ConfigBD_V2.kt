package fr.hamtec.bd

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import fr.hamtec.routes.log
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


suspend fun configurationBD_v2() {
    withContext(Dispatchers.IO) {

        // Configurez HikariCP d'abord
        try {
            val config = HikariConfig().apply {
                jdbcUrl = "jdbc:postgresql://localhost:5432/teams"
                driverClassName = "org.postgresql.Driver"
                username = "postgres"
                password = "Wap74Q+2"
            }

            val dataSource = HikariDataSource(config)
            Database.connect(dataSource)
            log.info("Connexion à la base de données effectuée avec succès via HikariCP !")

            // Passez le DataSource à Flyway pour les migrations
            Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .load()
                .migrate()
            log.info("Migrations de la nouvelle base de données effectuées avec succès via Flyway !")

            transaction {
                log.info("*****- Création des tables Teams et Players")
                SchemaUtils.create(Teams, Players)
                log.info("*****- Tables créées avec succès, schéma initialiser")
            }

        } catch(e: Exception) {
            log.error("Erreur de connexion à la base de données via HikariCP/Flyway *** : ${e.message}", e)
        }
    }
}


