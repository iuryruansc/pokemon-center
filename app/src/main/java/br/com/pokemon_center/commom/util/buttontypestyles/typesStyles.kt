package br.com.pokemon_center.commom.util.buttontypestyles

import br.com.pokemon_center.R

internal fun typesStyles(type: String): TypeStyle? {
    val styles = mapOf(
        "fire" to TypeStyle(R.drawable.type_fire, R.color.md_theme_light_error),
        "water" to TypeStyle(R.drawable.type_water, R.color.md_theme_dark_tertiaryContainer),
        "grass" to TypeStyle(R.drawable.type_grass, R.color.md_theme_light_primary),
        "normal" to TypeStyle(R.drawable.type_normal, R.color.md_theme_light_secondaryContainer),
        "flying" to TypeStyle(R.drawable.type_flying, R.color.flying),
        "poison" to TypeStyle(R.drawable.type_poison, R.color.poison),
        "electric" to TypeStyle(R.drawable.type_electric, R.color.electric),
        "ice" to TypeStyle(R.drawable.type_ice, R.color.ice),
        "rock" to TypeStyle(R.drawable.type_rock, R.color.rock),
        "psychic" to TypeStyle(R.drawable.type_psychic, R.color.psychic),
        "fighting" to TypeStyle(R.drawable.type_fighting, R.color.fighting),
        "dark" to TypeStyle(R.drawable.type_dark, R.color.md_theme_light_inverseSurface),
        "ghost" to TypeStyle(R.drawable.type_ghost, R.color.ghost),
        "fairy" to TypeStyle(R.drawable.type_fairy, R.color.fairy),
        "steel" to TypeStyle(R.drawable.type_steel, R.color.steel),
        "dragon" to TypeStyle(R.drawable.type_dragon, R.color.dragon),
        "bug" to TypeStyle(R.drawable.type_bug, R.color.bug),
        "ground" to TypeStyle(R.drawable.type_ground, R.color.ground)
    )

    return styles[type]
}
