package br.com.pokemoncenter.commom.util.hofs.types

import br.com.pokemon_center.R

fun getTypeResources(type: String): Pair<Int, Int>? {
    return when (type) {
        "normal" -> Pair(R.drawable.type_normal, R.drawable.type_normal_background)
        "fighting" -> Pair(R.drawable.type_fighting, R.drawable.type_fighting_background)
        "flying" -> Pair(R.drawable.type_flying, R.drawable.type_flying_background)
        "poison" -> Pair(R.drawable.type_poison, R.drawable.type_poison_background)
        "ground" -> Pair(R.drawable.type_ground, R.drawable.type_ground_background)
        "rock" -> Pair(R.drawable.type_rock, R.drawable.type_rock_background)
        "bug" -> Pair(R.drawable.type_bug, R.drawable.type_bug_background)
        "ghost" -> Pair(R.drawable.type_ghost, R.drawable.type_ghost_background)
        "steel" -> Pair(R.drawable.type_steel, R.drawable.type_steel_background)
        "fire" -> Pair(R.drawable.type_fire, R.drawable.type_fire_background)
        "water" -> Pair(R.drawable.type_water, R.drawable.type_water_background)
        "grass" -> Pair(R.drawable.type_grass, R.drawable.type_grass_background)
        "electric" -> Pair(R.drawable.type_electric, R.drawable.type_electric_background)
        "psychic" -> Pair(R.drawable.type_psychic, R.drawable.type_psychic_background)
        "ice" -> Pair(R.drawable.type_ice, R.drawable.type_ice_background)
        "dragon" -> Pair(R.drawable.type_dragon, R.drawable.type_dragon_background)
        "dark" -> Pair(R.drawable.type_dark, R.drawable.type_dark_background)
        "fairy" -> Pair(R.drawable.type_fairy, R.drawable.type_fairy_background)
        else -> null
    }
}
