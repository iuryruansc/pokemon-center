package br.com.pokemoncenter.data.api.models.pokemonbynamedata

import br.com.pokemoncenter.data.api.models.nestedmodels.Stat
import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: Stat
)
