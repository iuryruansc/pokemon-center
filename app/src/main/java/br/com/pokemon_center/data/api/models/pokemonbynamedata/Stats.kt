package br.com.pokemon_center.data.api.models.pokemonbynamedata

import br.com.pokemon_center.data.api.models.nestedmodels.Stat
import com.google.gson.annotations.SerializedName

data class Stats (
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: Stat
)
