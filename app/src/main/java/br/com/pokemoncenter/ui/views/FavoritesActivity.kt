package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.pokemon_center.databinding.ActivityFavoritesBinding
import br.com.pokemoncenter.commom.util.listeners.ActivityFavoritesListener
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView
import br.com.pokemoncenter.ui.adapters.PokemonCardAdapter
import br.com.pokemoncenter.ui.viewmodels.FavoritesViewModel

class FavoritesActivity : AppCompatActivity(), ActivityFavoritesListener {

    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private val adapter = PokemonCardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val favoritesRecyclerView = binding.favoritesRv

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener {
            menuItemClick(it, binding.drawerLayout)
        }

        adapter.setCardListener(this)

        favoritesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        favoritesRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
        viewModel.favoritesList.observe(this) { favorites ->
            adapter.submitList(favorites)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.favoritesList.removeObservers(this)
    }

    override fun onItemClick(view: View, position: Int, pokemonName: CharSequence) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra("pokemon", pokemonName.toString().lowercase())
        startActivity(intent)
    }
}
