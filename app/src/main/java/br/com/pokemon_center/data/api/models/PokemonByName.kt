package br.com.pokemon_center.data.api.models

import com.google.gson.annotations.SerializedName

data class PokemonByName (
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    @SerializedName("is_default")
    val weight: Int,
    val abilities: List<Abilities>,
)