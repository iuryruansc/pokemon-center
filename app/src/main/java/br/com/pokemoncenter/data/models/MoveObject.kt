package br.com.pokemoncenter.data.models

import br.com.pokemoncenter.data.api.models.nestedmodels.Type

data class MoveObject(
    val name: String,
    val pp: Int,
    val type: Type?,
    val typeImage: Int,
    val typeBackground: Int
)
