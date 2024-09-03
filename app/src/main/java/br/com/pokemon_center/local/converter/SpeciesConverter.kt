package br.com.pokemon_center.local.converter

import androidx.room.TypeConverter
import br.com.pokemon_center.data.api.models.speciesbynamedata.FlavorTextEntry
import br.com.pokemon_center.data.api.models.speciesbynamedata.Genera
import br.com.pokemon_center.data.api.models.speciesbynamedata.Generation
import br.com.pokemon_center.data.api.models.speciesbynamedata.Species
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpeciesConverter {

    @TypeConverter
    fun fromSpecies(species: Species): String {
        return Gson().toJson(species)
    }

    @TypeConverter
    fun toSpecies(speciesString: String): Species {
        val type = object : TypeToken<Species>() {}.type
        return Gson().fromJson(speciesString, type)
    }

    @TypeConverter
    fun fromGeneration(generation: Generation): String {
        return Gson().toJson(generation)
    }

    @TypeConverter
    fun toGeneration(generationString: String): Generation {
        val type = object : TypeToken<Generation>() {}.type
        return Gson().fromJson(generationString, type)
    }

    @TypeConverter
    fun fromFlavorTextEntries(flavorTextEntries: List<FlavorTextEntry>): String {
        return Gson().toJson(flavorTextEntries)
    }

    @TypeConverter
    fun toFlavorTextEntries(flavorTextEntriesString: String): List<FlavorTextEntry> {
        val type = object : TypeToken<List<FlavorTextEntry>>() {}.type
        return Gson().fromJson(flavorTextEntriesString, type)
    }

    @TypeConverter
    fun fromGenera(genera: List<Genera>): String {
        return Gson().toJson(genera)
    }

    @TypeConverter
    fun toGenera(generaString: String): List<Genera> {
        val type = object : TypeToken<List<Genera>>() {}.type
        return Gson().fromJson(generaString, type)
    }
}
