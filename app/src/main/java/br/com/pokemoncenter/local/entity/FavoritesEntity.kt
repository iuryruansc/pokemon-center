package br.com.pokemoncenter.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_card")
data class FavoritesEntity(
    @PrimaryKey
    val name: String,
    val sprite: String,
    val backgroundOne: Int?,
    val backgroundTwo: Int?
)
