package br.com.pokemon_center.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.pokemon_center.local.converter.PokemonConverter
import br.com.pokemon_center.local.converter.SpeciesConverter
import br.com.pokemon_center.local.dao.PokemonDao
import br.com.pokemon_center.local.entity.PokemonByNameEntity
import br.com.pokemon_center.local.entity.SpeciesByNameEntity

@Database(entities = [PokemonByNameEntity::class, SpeciesByNameEntity::class], version = 4, exportSchema = false)
@TypeConverters(SpeciesConverter::class, PokemonConverter::class)

abstract class AppDatabase: RoomDatabase() {
    abstract fun pokemonDao() : PokemonDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "pokemon_database.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}
