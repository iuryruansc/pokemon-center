package br.com.pokemoncenter.commom.util.hofs.types

import br.com.pokemoncenter.commom.Constants

fun getCombinedTypeEffectivenessDef(
    type1: String,
    type2: String?,
    typeEffectivenessMap: Map<String, TypeEffectiveness>
): Map<String, Double> {
    val typeEffectiveness1 = typeEffectivenessMap[type1] ?: return emptyMap()

    val combinedEffectiveness = mutableMapOf<String, Double>()

    for (type in typeEffectivenessMap.keys) {
        combinedEffectiveness[type] =
            Constants.EFFECTIVE_NORMAL // Initialize with normal effectiveness
    }

    // Apply defense effectiveness for type1
    applyTypeEffectiveness(typeEffectiveness1.defense, combinedEffectiveness)

    // Apply defense effectiveness for type2
    if (type2 != null) {
        val typeEffectiveness2 = typeEffectivenessMap[type2] ?: return emptyMap()
        applyTypeEffectiveness(typeEffectiveness2.defense, combinedEffectiveness)
    }

    return combinedEffectiveness
}

private fun applyTypeEffectiveness(
    defenseEffectiveness: DefenseEffectiveness,
    combinedEffectiveness: MutableMap<String, Double>
) {
    for (type in defenseEffectiveness.double) {
        combinedEffectiveness[type] =
            combinedEffectiveness[type]?.times(Constants.EFFECTIVE_2X) ?: Constants.EFFECTIVE_2X
    }
    for (type in defenseEffectiveness.half) {
        combinedEffectiveness[type] =
            combinedEffectiveness[type]?.times(Constants.EFFECTIVE_HALF) ?: Constants.EFFECTIVE_HALF
    }
    for (type in defenseEffectiveness.zero) {
        combinedEffectiveness[type] = Constants.NO_EFFECT
    }
}
