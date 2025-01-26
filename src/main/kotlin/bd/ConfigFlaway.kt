package fr.hamtec.bd

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database


fun migrateDatabase() {
    Database.connect(
        //-Configuration de la connexion à la base de données
        url = "jdbc:postgresql://localhost:5432/teams",
        driver = "org.postgresql.Driver"
    )
    val config = HikariConfig()
    config.jdbcUrl = "jdbc:postgresql://localhost:5432/teams"
    config.driverClassName = "org.postgresql.Driver"
    config.username = "postgres"
    config.password = "Wap74Q+2"
    val dataSource = HikariDataSource(config)


    Flyway.configure()
        .dataSource(dataSource)
        .load()
        .migrate()

    Flyway.configure().locations("kotlin/bd")

//    val dataSource = Database.connect(
//        url = "jdbc:postgresql://localhost:5432/teams",
//        driver = "org.postgresql.Driver"
//    )
//    Flyway.configure()
//        .dataSource(dataSource.toString(), "postgres", "Wap74Q+2")
//        .locations("kotlin/bd")
//
//    Flyway.configure().load().migrate()
}
