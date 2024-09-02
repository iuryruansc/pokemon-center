package br.com.pokemon_center.commom.util.typechart

fun getCombinedTypeEffectivenessAtk(
    type1: String,
    type2: String?,
    typeEffectivenessMap: Map<String, TypeEffectiveness>
): Map<String, Double> {

    val typeEffectiveness1 = typeEffectivenessMap[type1] ?: return emptyMap()

    val combinedEffectiveness = mutableMapOf<String, Double>()

    for (type in typeEffectivenessMap.keys) {
        combinedEffectiveness[type] = 1.0 // Initialize with normal effectiveness
    }

    // Apply attack effectiveness for type1
    applyTypeEffectivenessAtk(typeEffectiveness1.attack, combinedEffectiveness)

    // Apply attack effectiveness for type2
    if (type2 != null) {
        val typeEffectiveness2 = typeEffectivenessMap[type2] ?: return emptyMap()
        applyTypeEffectivenessAtk(typeEffectiveness2.attack, combinedEffectiveness)
    }

    return combinedEffectiveness
}

private fun applyTypeEffectivenessAtk(
    attackEffectiveness: AttackEffectiveness,
    combinedEffectiveness: MutableMap<String, Double>
) {
    for (type in attackEffectiveness.double) {
        combinedEffectiveness[type] = combinedEffectiveness[type]?.times(2.0) ?: 2.0
    }
    for (type in attackEffectiveness.half) {
        combinedEffectiveness[type] = combinedEffectiveness[type]?.times(0.5) ?: 0.5
    }
    for (type in attackEffectiveness.zero) {
        combinedEffectiveness[type] = 0.0
    }
}

