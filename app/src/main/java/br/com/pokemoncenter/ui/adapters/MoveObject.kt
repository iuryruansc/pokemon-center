package br.com.pokemoncenter.ui.adapters

import br.com.pokemoncenter.data.api.models.nestedmodels.Type

data class MoveObject(
    val name: String,
    val pp: Int,
    val type: Type?,
    val typeImage: Int,
    val typeBackground: Int
)
