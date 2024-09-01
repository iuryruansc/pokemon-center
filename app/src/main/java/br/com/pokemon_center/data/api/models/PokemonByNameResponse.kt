package br.com.pokemon_center.data.api.models

import br.com.pokemon_center.data.api.models.pokemonbynamedata.Abilities
import br.com.pokemon_center.data.api.models.pokemonbynamedata.Cry
import br.com.pokemon_center.data.api.models.pokemonbynamedata.Moves
import br.com.pokemon_center.data.api.models.pokemonbynamedata.Sprites
import br.com.pokemon_center.data.api.models.pokemonbynamedata.Stats
import br.com.pokemon_center.data.api.models.pokemonbynamedata.Types
import com.google.gson.annotations.SerializedName

data class PokemonByNameResponse (
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val abilities: List<Abilities>,
    val moves: List<Moves>,
    val sprites: Sprites,
    val stats: List<Stats>,
    val types: List<Types>,
    val cries: Cry
)