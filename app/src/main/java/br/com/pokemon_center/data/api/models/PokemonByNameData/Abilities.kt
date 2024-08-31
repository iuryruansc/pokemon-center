package br.com.pokemon_center.data.api.models.PokemonByNameData

import br.com.pokemon_center.data.api.models.nestedmodels.Ability
import com.google.gson.annotations.SerializedName

data class Abilities(
    val ability: Ability,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int
)
