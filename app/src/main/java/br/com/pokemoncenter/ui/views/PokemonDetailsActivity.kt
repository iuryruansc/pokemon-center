package br.com.pokemoncenter.ui.views

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityPokemonDetailsBinding
import br.com.pokemoncenter.commom.util.hofs.textformat.capitalizedName
import br.com.pokemoncenter.commom.util.hofs.types.typeStyle
import br.com.pokemoncenter.data.models.ViewModelFactory
import br.com.pokemoncenter.ui.adapters.PokemonDetailsPagerAdapter
import br.com.pokemoncenter.ui.viewmodels.PokemonDetailsViewModel
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private val viewModel: PokemonDetailsViewModel by viewModels { ViewModelFactory(application) }
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra("pokemon")
            ?: return
        viewModel.pokemonDetails(pokemonName)

        var cryUrl: String? = null

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemon.collect { pokemon ->
                    if (pokemon != null) {
                        binding.id.text = String.format(Locale.ROOT, "#%03d", pokemon.id)
                        binding.name.text = capitalizedName(pokemon.name)
                        binding.sprite.load(pokemon.sprites.frontDefault)
                        binding.type1.load(typeStyle(pokemon.types[0].type.name))
                        val type1 = pokemon.types[0].type.name
                        var type2: String? = null
                        if (pokemon.types.size > 1) {
                            binding.type2.visibility = View.VISIBLE
                            binding.type2.load(typeStyle(pokemon.types[1].type.name))
                            type2 = pokemon.types[1].type.name
                        }
                        cryUrl = pokemon.cries.latest

                        val pagerAdapter = PokemonDetailsPagerAdapter(
                            this@PokemonDetailsActivity,
                            pokemonName,
                            type1,
                            type2
                        )

                        binding.viewpagerInfo.adapter = pagerAdapter

                        TabLayoutMediator(
                            binding.detailsTabLayout,
                            binding.viewpagerInfo
                        ) { tab, position ->
                            when (position) {
                                0 -> tab.text = getString(R.string.info)
                                1 -> tab.text = getString(R.string.stats)
                                2 -> tab.text = getString(R.string.effectiveness)
                                3 -> tab.text = getString(R.string.moves)
                            }
                        }.attach()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.detailsConstraintLayout.visibility =
                        if (isLoading) View.GONE else View.VISIBLE
                    binding.detailsLinearLayout.visibility =
                        if (isLoading) View.GONE else View.VISIBLE
                    binding.progressIndicator.visibility =
                        if (isLoading) View.VISIBLE else View.GONE
                    viewModel.errorMessage.observe(this@PokemonDetailsActivity) { message ->
                        Toast.makeText(this@PokemonDetailsActivity, message, Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                }
            }
        }

        binding.sprite.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                stopMediaPlayer()
            } else {
                cryUrl?.let { url ->
                    playMediaPlayer(url)
                }
            }
        }
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
}
