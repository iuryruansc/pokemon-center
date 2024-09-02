package br.com.pokemon_center.commom.util.typechart

data class TypeEffectiveness(
    val attack: AttackEffectiveness,
    val defense: DefenseEffectiveness
)


data class AttackEffectiveness(
    val double: List<String>,
    val half: List<String>,
    val zero: List<String>
)


data class DefenseEffectiveness(
    val double: List<String>,
    val half: List<String>,
    val zero: List<String>
)
