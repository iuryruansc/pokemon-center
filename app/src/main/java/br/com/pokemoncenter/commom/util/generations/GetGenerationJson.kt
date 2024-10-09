package br.com.pokemoncenter.commom.util.generations

import br.com.pokemon_center.R

fun getGenerationJson(logo: Int) : String? {
    val logos = mapOf(
        R.drawable.logo_gen3 to "generations/generation_3.json",
        R.drawable.logo_gen4 to "generations/generation_4.json",
        R.drawable.logo_gen5 to "generations/generation_5.json",
        R.drawable.logo_gen6 to "generations/generation_6.json",
        R.drawable.logo_gen7 to "generations/generation_7.json",
        R.drawable.logo_gen8 to "generations/generation_8.json",
        R.drawable.logo_gen9 to "generations/generation_9.json",
        R.drawable.logo_gen3_remakes to "generations/generation_3_remakes.json",
        R.drawable.logo_gen4_remakes to "generations/generation_4_remakes.json",
        R.drawable.logo_gen6_remakes to "generations/generation_6_remakes.json",
        R.drawable.logo_gen7_remakes to "generations/generation_7_remakes.json",
        R.drawable.logo_gen8_remakes to "generations/generation_8_remakes.json",
        R.drawable.logo_gen7_remakespt2 to "generations/generation_7_remakespt2.json",
        R.drawable.logo_gen5pt2 to "generations/generation_5pt2.json",
        R.drawable.logo_gen8pt2 to "generations/generation_8pt2.json",
        R.drawable.logo_gen8_dlcs to "generations/generation_8_dlc.json",
        R.drawable.logo_gen9_dlcs to "generations/generation_9_dlc.json"
    )
    return logos[logo]
}
