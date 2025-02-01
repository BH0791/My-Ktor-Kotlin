package fr.hamtec.bd

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import fr.hamtec.routes.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database


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

        } catch(e: Exception) {
            log.error("Erreur de connexion à la base de données via HikariCP/Flyway *** : ${e.message}", e)
        }
    }
}


