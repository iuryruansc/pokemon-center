package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityPokemonDetailsBinding
import br.com.pokemoncenter.commom.util.hofs.textformat.capitalizedName
import br.com.pokemoncenter.commom.util.hofs.types.typeStyle
import br.com.pokemoncenter.commom.util.ui.setupNavigationView
import br.com.pokemoncenter.commom.util.ui.showErrorDialog
import br.com.pokemoncenter.data.models.ViewModelFactory
import br.com.pokemoncenter.ui.adapters.PokemonDetailsPagerAdapter
import br.com.pokemoncenter.ui.viewmodels.PokemonDetailsViewModel
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonDetailsActivity : AppCompatActivity(), OnMenuItemClickListener {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private val viewModel: PokemonDetailsViewModel by viewModels { ViewModelFactory(application) }
    private var mediaPlayer: MediaPlayer? = null
    private var cryUrl: String? = null
    private var pokemonName: String = ""

    enum class TabIndex {
        INFO, STATS, EFFECTIVENESS, MOVES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonName = intent.getStringExtra("pokemon")
            ?: return
        viewModel.pokemonDetails(pokemonName)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeFavorite() }
                launch { observePokemon(pokemonName) }
                launch { observeLoading() }
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener(this)
    }

    private suspend fun observePokemon(pokemonName: String) {
        viewModel.pokemon.collect { pokemon ->
            if (pokemon != null) {
                with(binding) {
                    sprite.load(pokemon.sprites.frontDefault)
                    type1.load(typeStyle(pokemon.types[0].type.name))
                    id.text = String.format(Locale.ROOT, "#%03d", pokemon.id)
                    name.text = capitalizedName(pokemon.name)
                    val type1 = pokemon.types[0].type.name
                    var secondType: String? = null
                    if (pokemon.types.size > 1) {
                        type2.load(typeStyle(pokemon.types[1].type.name))
                        type2.visibility = View.VISIBLE
                        secondType = pokemon.types[1].type.name
                    }
                    cryUrl = pokemon.cries.latest

                    val pagerAdapter = PokemonDetailsPagerAdapter(
                        this@PokemonDetailsActivity,
                        pokemonName,
                        type1,
                        secondType
                    )

                    binding.viewpagerInfo.adapter = pagerAdapter
                    setupTabLayout()
                    hideLoading()
                }
            }
        }
    }

    private suspend fun observeLoading() {
        viewModel.isLoading.collect { isLoading ->
            if (isLoading) {
                showLoading()
                viewModel.errorMessage.observe(this@PokemonDetailsActivity) {
                    showError(it)
                }
            }
        }
    }

    private suspend fun observeFavorite() {

        lifecycleScope.launch {
            val initialIsFavorite = viewModel.alreadyFavorite(pokemonName)
            setFavoriteIcon(initialIsFavorite)
        }

        viewModel.isFavorite.collect { isFavorite ->
            setFavoriteIcon(isFavorite)
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.topAppBar.menu.findItem(R.id.menu_favorite).setIcon(R.drawable.favorite_icon)
        } else {
            binding.topAppBar.menu.findItem(R.id.menu_favorite)
                .setIcon(R.drawable.not_favorite_icon)
        }
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            binding.detailsTabLayout,
            binding.viewpagerInfo
        ) { tab, position ->
            tab.text = when (position) {
                TabIndex.INFO.ordinal -> getString(R.string.info)
                TabIndex.STATS.ordinal -> getString(R.string.stats)
                TabIndex.EFFECTIVENESS.ordinal -> getString(R.string.effectiveness)
                TabIndex.MOVES.ordinal -> getString(R.string.moves)
                else -> ""
            }
        }.attach()
    }

    private fun hideLoading() {
        binding.detailsConstraintLayout.visibility = View.VISIBLE
        binding.detailsLinearLayout.visibility = View.VISIBLE
        binding.progressIndicator.visibility = View.GONE
    }

    private fun showLoading() {
        binding.detailsConstraintLayout.visibility = View.GONE
        binding.detailsLinearLayout.visibility = View.GONE
        binding.progressIndicator.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        showErrorDialog(this, message)
    }

    private fun stopMediaPlayer() {
        mediaPlayer?.pause()
        binding.sprite.clearAnimation()
    }

    private fun playMediaPlayer(url: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                val duration = this.duration
                start()
                val pulseAnimation = AnimationUtils
                    .loadAnimation(this@PokemonDetailsActivity, R.anim.pulse)
                binding.sprite.startAnimation(pulseAnimation)

                Handler(Looper.getMainLooper())
                    .postDelayed({ binding.sprite.clearAnimation() }, duration.toLong())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.menu_sound -> {
                if (mediaPlayer?.isPlaying == true) {
                    stopMediaPlayer()
                } else {
                    cryUrl?.let { url -> playMediaPlayer(url) }
                }
                true
            }

            R.id.menu_favorite -> {
                viewModel.pokemonToFavorite(pokemonName)
                true
            }

            R.id.menu_home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }

            R.id.menu_drawer -> {
                binding.drawerLayout.open()
                true
            }

            else -> false
        }
    }
}
