package br.com.pokemoncenter.data.api.models.speciesbynamedata

import br.com.pokemoncenter.data.api.models.nestedmodels.Language

data class Genera(
    val genus: String,
    val language: Language
)
