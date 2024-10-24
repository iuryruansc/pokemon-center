package br.com.pokemoncenter.commom.util.hofs.jsonparse

import br.com.pokemoncenter.commom.util.hofs.types.AttackEffectiveness
import br.com.pokemoncenter.commom.util.hofs.types.DefenseEffectiveness
import br.com.pokemoncenter.commom.util.hofs.types.TypeEffectiveness
import org.json.JSONObject

fun parseTypeEffectiveness(jsonString: String): Map<String, TypeEffectiveness> {
    return try {
        val jsonObject = JSONObject(jsonString)
        val typeEffectivenessMap = mutableMapOf<String, TypeEffectiveness>()

        for (typeName in jsonObject.keys()) {
            val typeJson = jsonObject.getJSONObject(typeName)
            val attackJson = typeJson.getJSONObject("attack")
            val defenseJson = typeJson.getJSONObject("defense")

            val attackEffectiveness = AttackEffectiveness(
                jsonArrayToList(attackJson.getJSONArray("double")),
                jsonArrayToList(attackJson.getJSONArray("half")),
                jsonArrayToList(attackJson.getJSONArray("zero"))
            )
            val defenseEffectiveness = DefenseEffectiveness(
                jsonArrayToList(defenseJson.getJSONArray("double")),
                jsonArrayToList(defenseJson.getJSONArray("half")),
                jsonArrayToList(defenseJson.getJSONArray("zero"))
            )

            typeEffectivenessMap[typeName] =
                TypeEffectiveness(attackEffectiveness, defenseEffectiveness)
        }

        return typeEffectivenessMap
    } catch (e: Exception) {
        emptyMap()
    }
}
