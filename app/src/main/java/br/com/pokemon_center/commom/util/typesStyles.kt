package br.com.pokemon_center.commom.util

import br.com.pokemon_center.R

internal fun typesStyles(type: String): Int? {
    val styles = mapOf(
        "fire" to R.drawable.type_fire,
        "water" to R.drawable.type_water,
        "grass" to R.drawable.type_grass,
        "normal" to R.drawable.type_normal,
        "flying" to R.drawable.type_flying,
        "poison" to R.drawable.type_poison,
        "electric" to R.drawable.type_electric,
        "ice" to R.drawable.type_ice,
        "rock" to R.drawable.type_rock,
        "psychic" to R.drawable.type_psychic,
        "fighting" to R.drawable.type_fighting,
        "dark" to R.drawable.type_dark,
        "ghost" to R.drawable.type_ghost,
        "fairy" to R.drawable.type_fairy,
        "steel" to R.drawable.type_steel,
        "dragon" to R.drawable.type_dragon,
        "bug" to R.drawable.type_bug,
        "ground" to R.drawable.type_ground
    )

    return styles[type]
}
