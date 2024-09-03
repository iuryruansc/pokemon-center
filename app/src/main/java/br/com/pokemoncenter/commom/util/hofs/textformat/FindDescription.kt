package br.com.pokemoncenter.commom.util.hofs.textformat

import br.com.pokemoncenter.data.api.models.speciesbynamedata.FlavorTextEntry
import java.util.Locale

fun findDescription(description: List<FlavorTextEntry>): String {
    val systemLanguage = Locale.getDefault().language
    return description.find { it.language.name == systemLanguage }?.flavorText?.replace("\n", "")
        ?: description.find { it.language.name == "en" }?.flavorText?.replace("\n", "")
        ?: "No description available"
}
