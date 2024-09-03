package br.com.pokemoncenter.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.pokemoncenter.local.entity.PokemonByNameEntity
import br.com.pokemoncenter.local.entity.SpeciesByNameEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_by_name")
    suspend fun getAllPokemon(): List<PokemonByNameEntity>

    @Query("SELECT * FROM pokemon_by_name WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonByNameEntity?

    @Query("SELECT * FROM pokemon_by_name WHERE name = :name")
    suspend fun getPokemonByName(name: String): PokemonByNameEntity?

    @Query("SELECT * FROM species_by_name WHERE pokemonName = :name")
    suspend fun getSpeciesByName(name: String): SpeciesByNameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonByNameEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(species: SpeciesByNameEntity): Long
}
