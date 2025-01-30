package fr.hamtec.dao

import fr.hamtec.bd.Teams
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TeamDAO(
    id: EntityID<Int>,
) : IntEntity(id)
{
    companion object : IntEntityClass<TeamDAO>(Teams)
    var name by Teams.name
}
