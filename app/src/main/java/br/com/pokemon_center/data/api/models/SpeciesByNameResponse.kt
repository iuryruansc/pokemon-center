package br.com.pokemon_center.data.api.models

import br.com.pokemon_center.data.api.models.speciesbynamedata.FlavorTextEntry
import br.com.pokemon_center.data.api.models.speciesbynamedata.Genera
import br.com.pokemon_center.data.api.models.speciesbynamedata.Generation
import br.com.pokemon_center.data.api.models.speciesbynamedata.Species
import com.google.gson.annotations.SerializedName

data class SpeciesByNameResponse (
    val id: Int,
    val name: String,
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: Species?,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    val generation: Generation,
    val genera: List<Genera>,
    @SerializedName("capture_rate")
    val captureRate: Int
)
