package br.com.pokemon_center.data.api.models

import br.com.pokemon_center.data.api.models.PokemonByNameData.Abilities
import br.com.pokemon_center.data.api.models.PokemonByNameData.Moves
import br.com.pokemon_center.data.api.models.PokemonByNameData.Sprites
import br.com.pokemon_center.data.api.models.PokemonByNameData.Stats
import br.com.pokemon_center.data.api.models.PokemonByNameData.Types
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
)