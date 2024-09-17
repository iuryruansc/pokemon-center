package br.com.pokemoncenter.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.pokemoncenter.local.entity.FavoritesEntity
import br.com.pokemoncenter.local.entity.MoveEntity
import br.com.pokemoncenter.local.entity.PokemonByNameEntity
import br.com.pokemoncenter.local.entity.SpeciesByNameEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM move_details")
    suspend fun getAllMoves(): List<MoveEntity>

    @Query("SELECT * FROM pokemon_card")
    suspend fun getAllFavorites(): List<FavoritesEntity?>

    @Query("SELECT * FROM pokemon_card WHERE name = :name")
    suspend fun getFavorite(name: String): FavoritesEntity?

    @Query("SELECT * FROM pokemon_by_name WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonByNameEntity?

    @Query("SELECT * FROM pokemon_by_name WHERE name = :name")
    suspend fun getPokemonByName(name: String): PokemonByNameEntity?

    @Query("SELECT * FROM species_by_name WHERE pokemonName = :name")
    suspend fun getSpeciesByName(name: String): SpeciesByNameEntity?

    @Query("SELECT * FROM move_details WHERE name = :name")
    suspend fun getMoveByName(name: String): MoveEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMove(move: MoveEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: PokemonByNameEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSpecies(species: SpeciesByNameEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(pokemonCard: FavoritesEntity): Long

    @Delete
    suspend fun deleteFavorite(pokemonCard: FavoritesEntity)
}
