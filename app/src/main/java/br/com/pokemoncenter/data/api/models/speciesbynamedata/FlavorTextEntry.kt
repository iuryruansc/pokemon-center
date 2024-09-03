package br.com.pokemoncenter.data.api.models.speciesbynamedata

import br.com.pokemoncenter.data.api.models.nestedmodels.Language
import com.google.gson.annotations.SerializedName

data class FlavorTextEntry(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: Language
)
