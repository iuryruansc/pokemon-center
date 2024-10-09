package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import br.com.pokemon_center.databinding.ActivityPokedexListBinding
import br.com.pokemoncenter.commom.util.hofs.jsonparse.parsePokedexJson
import br.com.pokemoncenter.commom.util.listeners.PokemonNameListener
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView
import br.com.pokemoncenter.ui.adapters.PokedexAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class RegionDetailsActivity : AppCompatActivity(), PokemonNameListener {

    private lateinit var binding: ActivityPokedexListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.pokedexLayout

        val json = intent.getStringExtra("region") ?: return
        val regionName = intent.getStringExtra("regionName") ?: return

        lifecycleScope.launch {
            val pokedexItems = withContext(Dispatchers.Default) {
                parsePokedexJson(json)
            }

            val adapter = PokedexAdapter(pokedexItems)
            adapter.setChoiceListener(this@RegionDetailsActivity)

            recyclerView.layoutManager = GridLayoutManager(this@RegionDetailsActivity, 2)
            recyclerView.adapter = adapter

        }

        binding.topAppBar.title = String.format(Locale.ROOT, "%s Pokédex", regionName)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener {
            menuItemClick(it, binding.drawerLayout)
        }

    }

    override fun onItemClick(view: View, position: Int, mult: CharSequence) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra("pokemon", mult.toString().lowercase())
        startActivity(intent)
    }
}