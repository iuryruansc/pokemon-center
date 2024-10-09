package br.com.pokemoncenter.commom.util.hofs.jsonparse

import br.com.pokemoncenter.data.models.Pokemon
import org.json.JSONObject

fun parsePokedexJson(jsonString: String): List<Pokemon> {
    val pokedex = mutableListOf<Pokemon>()
    val jsonObject = JSONObject(jsonString)
    val pokedexArray = jsonObject.getJSONArray("pokemon")

    for (i in 0 until pokedexArray.length()) {
        val pokedexObject = pokedexArray.getJSONObject(i)
        val name = pokedexObject.getString("name")
        val image = pokedexObject.getString("sprite")
        val type1 = pokedexObject.getString("type1")
        val type2 = pokedexObject.getString("type2")

        val pokemon = Pokemon(name, image, type1, type2)
        pokedex.add(pokemon)
    }
    return pokedex
}
