package br.com.pokemoncenter.local.converter

import androidx.room.TypeConverter
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Abilities
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Cry
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Moves
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Sprites
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Stats
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Types
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonConverter {

    @TypeConverter
    fun fromSprite(sprites: Sprites): String {
        return Gson().toJson(sprites)
    }

    @TypeConverter
    fun toSprite(spritesString: String): Sprites {
        val type = object : TypeToken<Sprites>() {}.type
        return Gson().fromJson(spritesString, type)
    }

    @TypeConverter
    fun fromAbilities(abilities: List<Abilities>): String {
        return Gson().toJson(abilities)
    }

    @TypeConverter
    fun toAbilities(abilitiesString: String): List<Abilities> {
        val type = object : TypeToken<List<Abilities>>() {}.type
        return Gson().fromJson(abilitiesString, type)
    }

    @TypeConverter
    fun fromMoves(moves: List<Moves>): String {
        return Gson().toJson(moves)
    }

    @TypeConverter
    fun toMoves(movesString: String): List<Moves> {
        val type = object : TypeToken<List<Moves>>() {}.type
        return Gson().fromJson(movesString, type)
    }

    @TypeConverter
    fun fromStats(stats: List<Stats>): String {
        return Gson().toJson(stats)
    }

    @TypeConverter
    fun toStats(statsString: String): List<Stats> {
        val type = object : TypeToken<List<Stats>>() {}.type
        return Gson().fromJson(statsString, type)
    }

    @TypeConverter
    fun fromTypes(types: List<Types>): String {
        return Gson().toJson(types)
    }

    @TypeConverter
    fun toTypes(typesString: String): List<Types> {
        val type = object : TypeToken<List<Types>>() {}.type
        return Gson().fromJson(typesString, type)
    }

    @TypeConverter
    fun fromCry(cry: Cry): String {
        return Gson().toJson(cry)
    }

    @TypeConverter
    fun toCry(cryString: String): Cry {
        val type = object : TypeToken<Cry>() {}.type
        return Gson().fromJson(cryString, type)
    }
}
