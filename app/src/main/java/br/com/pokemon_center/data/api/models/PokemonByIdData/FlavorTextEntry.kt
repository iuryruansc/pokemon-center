package br.com.pokemon_center.data.api.models.PokemonByIdData

import br.com.pokemon_center.data.api.models.nestedmodels.Language
import com.google.gson.annotations.SerializedName

data class FlavorTextEntry (
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: Language,
)
