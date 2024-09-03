package br.com.pokemon_center.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.pokemon_center.data.api.models.speciesbynamedata.FlavorTextEntry
import br.com.pokemon_center.data.api.models.speciesbynamedata.Genera
import br.com.pokemon_center.data.api.models.speciesbynamedata.Generation
import br.com.pokemon_center.data.api.models.speciesbynamedata.Species

@Entity(tableName = "species_by_name")
data class SpeciesByNameEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 1,
    val pokemonId: Int,
    val pokemonName: String,
    val evolvesFromSpecies: Species?,
    val generation: Generation,
    val captureRate: Int,
    val flavorTextEntries: List<FlavorTextEntry>,
    val genera: List<Genera>
)
