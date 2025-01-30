package fr.hamtec.bd

import fr.hamtec.bd.Teams.id
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.charLength

object Teams : IntIdTable("teams") {
    val name: Column<String?> = varchar("name", 128).nullable()

    init {
        name.charLength().greater(3)
        index(
            customIndexName = "team_custom_id_index",
            isUnique = true,
            id,
            name
        )
    }
}

object Players : IntIdTable("players") {
    val firstName = varchar("first_name", 128).nullable()
    val teamId = reference(
        "team_id",
        Teams.id, fkName = "players_team_fk",
        onDelete = ReferenceOption.CASCADE
    )

    init {
        firstName.charLength().greater(3)

        index(
            customIndexName = "player_custom_id_index",
            isUnique = true,
            id,
            firstName
        )
    }
}