package fr.hamtec.bd

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.charLength

object Teams  : Table() {
    val id = integer("id")
    val name = varchar("name", 128).nullable()
    init {
        check {
            name.charLength().greater(3)
        }
        index("name_and_id", true, name, id)
    }
    val teamId = reference("team_id", Teams.id, fkName = "players_team_fk", onDelete = ReferenceOption.CASCADE)
}

object Players : Table() {
    val id = integer("id")
    val firstName = varchar("first_name", 128).nullable()
    val teamId = reference("team_id", Teams.id)
}