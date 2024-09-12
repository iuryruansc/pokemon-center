package br.com.pokemoncenter.commom.util.hofs.jsonparse

import br.com.pokemoncenter.data.models.Character
import br.com.pokemoncenter.data.models.Generation
import br.com.pokemoncenter.data.models.SectionItem
import com.google.gson.Gson

fun parseGenerationJson(jsonString: String): List<SectionItem> {
    val gson = Gson()
    val generation = gson.fromJson(jsonString, Generation::class.java)
    val sectionItems = mutableListOf<SectionItem>()

    addGeneralInfo(sectionItems, generation)
    addFeatures(sectionItems, generation)
    addCharacters(sectionItems, generation)

    return sectionItems
}

private fun addGeneralInfo(sectionItems: MutableList<SectionItem>, generation: Generation) {
    sectionItems.add(SectionItem.Header("General Info"))
    sectionItems.add(SectionItem.TextContent("Generation Name: ${generation.general.generationName}"))
    sectionItems.add(SectionItem.TextContent("Region: ${generation.general.region}"))
    sectionItems.add(SectionItem.TextContent("Introduced Pokemon: ${generation.general.introducedPokemon}"))
    sectionItems.add(SectionItem.TextContent("Start Dex Number: ${generation.general.startDexNumber}"))
    sectionItems.add(SectionItem.TextContent("End Dex Number: ${generation.general.endDexNumber}"))
    sectionItems.add(SectionItem.TextContent("Games: ${generation.general.games}"))
    sectionItems.add(SectionItem.TextContent("Release Year: ${generation.general.releaseYear}"))
}

private fun addFeatures(sectionItems: MutableList<SectionItem>, generation: Generation) {
    sectionItems.add(SectionItem.Header("Features"))
    generation.features.forEach { feature ->
        sectionItems.add(SectionItem.TextContent(feature))
    }
}

private fun addCharacters(sectionItems: MutableList<SectionItem>, generation: Generation) {
    addCharacterSection(sectionItems, "Starters", generation.starters)
    addCharacterSection(sectionItems, "Legendary", generation.legendary)
    addCharacterSection(sectionItems, "Important Characters", generation.importantCharacters)
    addCharacterSection(sectionItems, "Gym Leaders/Trial Captains (Sun/Moon)", generation.gymLeaders)
    addCharacterSection(sectionItems, "Elite Four/Champion Cup (Sword/Shield)", generation.eliteFour)
}

private fun addCharacterSection(
    sectionItems: MutableList<SectionItem>,
    header: String,
    characters: List<Character>
) {
    sectionItems.add(SectionItem.Header(header))
    characters.forEach { character ->
        sectionItems.add(
            SectionItem.CharacterContent(character.name, character.image, character.role)
        )
    }
}
