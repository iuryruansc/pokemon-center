package br.com.pokemoncenter.data.api.models.pokemonbynamedata

import br.com.pokemoncenter.data.api.models.nestedmodels.Ability
import com.google.gson.annotations.SerializedName

data class Abilities(
    val ability: Ability,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int
)
