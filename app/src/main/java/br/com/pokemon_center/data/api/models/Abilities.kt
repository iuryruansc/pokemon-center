package br.com.pokemon_center.data.api.models

import com.google.gson.annotations.SerializedName

data class Abilities(
    val ability: Ability,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int
)
