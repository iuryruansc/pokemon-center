package br.com.pokemon_center.data.api.models

import br.com.pokemon_center.data.api.models.PokemonByIdData.FlavorTextEntry
import br.com.pokemon_center.data.api.models.PokemonByIdData.Species
import com.google.gson.annotations.SerializedName

data class SpeciesByIdResponse (
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: Species,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
)
