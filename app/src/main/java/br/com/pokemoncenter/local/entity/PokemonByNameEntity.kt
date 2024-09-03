package br.com.pokemoncenter.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Abilities
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Cry
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Moves
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Sprites
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Stats
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Types

@Entity(tableName = "pokemon_by_name")
data class PokemonByNameEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val abilities: List<Abilities>,
    val moves: List<Moves>,
    val stats: List<Stats>,
    val types: List<Types>,
    val cries: Cry
)
