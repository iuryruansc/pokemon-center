package br.com.pokemoncenter.commom.util.hofs.jsonparse

import br.com.pokemoncenter.data.models.Generation
import br.com.pokemoncenter.data.models.SectionItem
import com.google.gson.Gson

fun parseGenerationJson(jsonString: String): List<SectionItem> {
    val gson = Gson()
    val generation = gson.fromJson(jsonString, Generation::class.java)

    val sectionItems = mutableListOf<SectionItem>()

    sectionItems.add(SectionItem.Header("General Info"))
    sectionItems.add(SectionItem.TextContent("Generation Name: ${generation.general.generationName}"))
    sectionItems.add(SectionItem.TextContent("Region: ${generation.general.region}"))
    sectionItems.add(SectionItem.TextContent("Introduced Pokemon: ${generation.general.introducedPokemon}"))
    sectionItems.add(SectionItem.TextContent("Start Dex Number: ${generation.general.startDexNumber}"))
    sectionItems.add(SectionItem.TextContent("End Dex Number: ${generation.general.endDexNumber}"))
    sectionItems.add(SectionItem.TextContent("Games: ${generation.general.games}"))
    sectionItems.add(SectionItem.TextContent("Release Year: ${generation.general.releaseYear}"))

    sectionItems.add(SectionItem.Header("Features"))
    generation.features.forEach { feature ->
        sectionItems.add(SectionItem.TextContent(feature))
    }

    sectionItems.add(SectionItem.Header("Starters"))
    generation.starters.forEach { starter ->
        sectionItems.add(SectionItem.CharacterContent(starter.name, starter.image, starter.role))
    }

    sectionItems.add(SectionItem.Header("Legendary"))
    generation.legendary.forEach { legendary ->
        sectionItems.add(SectionItem.CharacterContent(legendary.name, legendary.image, legendary.role))
    }

    sectionItems.add(SectionItem.Header("Important Characters"))
    generation.importantCharacters.forEach { importantCharacter ->
        sectionItems.add(SectionItem.CharacterContent(importantCharacter.name, importantCharacter.image, importantCharacter.role))
    }

    sectionItems.add(SectionItem.Header("Gym Leaders/Trial Captains (Sun/Moon)"))
    generation.gymLeaders.forEach { gymLeader ->
        sectionItems.add(SectionItem.CharacterContent(gymLeader.name, gymLeader.image, gymLeader.role))
    }

    sectionItems.add(SectionItem.Header("Elite Four/Champion Cup (Sword/Shield)"))
    generation.eliteFour.forEach { eliteFour ->
        sectionItems.add(SectionItem.CharacterContent(eliteFour.name, eliteFour.image, eliteFour.role))
    }

    return sectionItems
}
