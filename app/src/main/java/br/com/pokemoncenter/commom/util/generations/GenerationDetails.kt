package br.com.pokemoncenter.commom.util.generations

import br.com.pokemon_center.R

val fileNames = listOf(
    "generations/generation_1.json",
    "generations/generation_2.json",
    "generations/generation_3.json",
    "generations/generation_4.json",
    "generations/generation_5.json",
    "generations/generation_6.json",
    "generations/generation_7.json",
    "generations/generation_8.json",
    "generations/generation_9.json"
)

fun generationDetails(gen: Int): List<Int>? {
    val jsons = mapOf(
        2 to listOf(R.drawable.logo_gen3, R.drawable.logo_gen3_remakes),
        3 to listOf(R.drawable.logo_gen4, R.drawable.logo_gen4_remakes),
        4 to listOf(R.drawable.logo_gen5, R.drawable.logo_gen5pt2),
        5 to listOf(R.drawable.logo_gen6, R.drawable.logo_gen6_remakes),
        6 to listOf(
            R.drawable.logo_gen7,
            R.drawable.logo_gen7_remakes,
            R.drawable.logo_gen7_remakespt2
        ),
        7 to listOf(
            R.drawable.logo_gen8,
            R.drawable.logo_gen8pt2,
            R.drawable.logo_gen8_remakes,
            R.drawable.logo_gen8_dlcs
        ),
        8 to listOf(
            R.drawable.logo_gen9,
            R.drawable.logo_gen9_dlcs
        )
    )
    return jsons[gen]
}
