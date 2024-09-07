package br.com.pokemoncenter.data.api.models.movedetailsresponse

import br.com.pokemoncenter.data.api.models.nestedmodels.Language
import com.google.gson.annotations.SerializedName

data class EffectEntry(
    val effect: String,
    val language: Language,
    @SerializedName("short_effect")
    val shortEffect: String
)
