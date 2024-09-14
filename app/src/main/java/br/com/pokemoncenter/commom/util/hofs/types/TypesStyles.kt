package br.com.pokemoncenter.commom.util.hofs.types

import br.com.pokemon_center.R

fun typeStyle(type: String): Int? {
    val styles = mapOf(
        "fire" to R.drawable.type_fire,
        "water" to R.drawable.type_water,
        "grass" to R.drawable.type_grass,
        "electric" to R.drawable.type_electric,
        "poison" to R.drawable.type_poison,
        "ground" to R.drawable.type_ground,
        "flying" to R.drawable.type_flying,
        "psychic" to R.drawable.type_psychic,
        "dark" to R.drawable.type_dark,
        "rock" to R.drawable.type_rock,
        "steel" to R.drawable.type_steel,
        "ghost" to R.drawable.type_ghost,
        "ice" to R.drawable.type_ice,
        "dragon" to R.drawable.type_dragon,
        "fairy" to R.drawable.type_fairy,
        "fighting" to R.drawable.type_fighting,
        "normal" to R.drawable.type_normal,
        "bug" to R.drawable.type_bug,
        "unknown" to R.drawable.type_unknown
    )

    return styles[type]
}

fun typeStyleBackground(type: String): Int? {
    val styles = mapOf(
        "fire" to R.drawable.type_fire_background,
        "water" to R.drawable.type_water_background,
        "grass" to R.drawable.type_grass_background,
        "electric" to R.drawable.type_electric_background,
        "poison" to R.drawable.type_poison_background,
        "ground" to R.drawable.type_ground_background,
        "flying" to R.drawable.type_flying_background,
        "psychic" to R.drawable.type_psychic_background,
        "dark" to R.drawable.type_dark_background,
        "rock" to R.drawable.type_rock_background,
        "steel" to R.drawable.type_steel_background,
        "ghost" to R.drawable.type_ghost_background,
        "ice" to R.drawable.type_ice_background,
        "dragon" to R.drawable.type_dragon_background,
        "fairy" to R.drawable.type_fairy_background,
        "fighting" to R.drawable.type_fighting_background,
        "normal" to R.drawable.type_normal_background,
        "bug" to R.drawable.type_bug_background,
        "unknown" to R.drawable.type_unknown_background
    )
    return styles[type]
}