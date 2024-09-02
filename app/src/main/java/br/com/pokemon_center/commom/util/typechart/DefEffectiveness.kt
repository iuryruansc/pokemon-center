package br.com.pokemon_center.commom.util.typechart

fun getCombinedTypeEffectivenessDef(
    type1: String,
    type2: String?,
    typeEffectivenessMap: Map<String, TypeEffectiveness>
): Map<String, Double> {

    val typeEffectiveness1 = typeEffectivenessMap[type1] ?: return emptyMap()


    val combinedEffectiveness = mutableMapOf<String, Double>()

    for (type in typeEffectivenessMap.keys) {
        combinedEffectiveness[type] = 1.0 // Initialize with normal effectiveness
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
        combinedEffectiveness[type] = combinedEffectiveness[type]?.times(2.0) ?: 2.0
    }
    for (type in defenseEffectiveness.half) {
        combinedEffectiveness[type] = combinedEffectiveness[type]?.times(0.5) ?: 0.5
    }
    for (type in defenseEffectiveness.zero) {
        combinedEffectiveness[type] = 0.0
    }
}