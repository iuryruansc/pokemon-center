package br.com.pokemoncenter.ui.views

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityPokemonDetailsBinding
import br.com.pokemoncenter.commom.util.hofs.textformat.capitalizedName
import br.com.pokemoncenter.commom.util.hofs.types.typeStyle
import br.com.pokemoncenter.ui.fragments.EffectivenessFragment
import br.com.pokemoncenter.ui.fragments.InfoFragment
import br.com.pokemoncenter.ui.fragments.MovesFragment
import br.com.pokemoncenter.ui.fragments.StatsFragment
import br.com.pokemoncenter.ui.viewmodels.PokemonDetailsViewModel
import br.com.pokemoncenter.ui.viewmodels.ViewModelFactory
import coil.load
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private val viewModel: PokemonDetailsViewModel by viewModels { ViewModelFactory(application) }
    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra("pokemon")
        viewModel.pokemonDetails(pokemonName!!)

        var cryUrl: String? = null

        val fragmentArgs = Bundle().apply { putString("pokemonName", pokemonName) }
        replaceFragment(InfoFragment(), fragmentArgs)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemon.collect { pokemon ->
                    if (pokemon != null) {
                        binding.id.text = String.format(Locale.ROOT, "#%03d", pokemon.id)
                        binding.name.text = capitalizedName(pokemon.name)
                        binding.sprite.load(pokemon.sprites.frontDefault)
                        binding.type1.load(typeStyle(pokemon.types[0].type.name))
                        fragmentArgs.putString("type1", pokemon.types[0].type.name)
                        if (pokemon.types.size > 1) {
                            updateType1Constraints()
                            binding.type2.load(typeStyle(pokemon.types[1].type.name))
                            fragmentArgs.putString("type2", pokemon.types[1].type.name)
                        }
                        cryUrl = pokemon.cries.latest
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
                mediaPlayer?.pause()
                binding.sprite.clearAnimation()
            } else {
                cryUrl?.let { url ->
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
            }
        }

        binding.detailsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> replaceFragment(InfoFragment(), fragmentArgs)
                    1 -> replaceFragment(StatsFragment(), fragmentArgs)
                    2 -> replaceFragment(EffectivenessFragment(), fragmentArgs)
                    3 -> replaceFragment(MovesFragment(), fragmentArgs)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun updateType1Constraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.detailsConstraintLayout)

        if (binding.type2.visibility == View.VISIBLE) {
            // If type2 is visible, constrain type1 to the start of the parent
            constraintSet.setHorizontalBias(binding.type1.id, 0.35f)
        } else {
            // If type2 is gone, constrain type1 to the center of the parent
            constraintSet.setHorizontalBias(binding.type1.id, 0.5f)
        }
        constraintSet.applyTo(binding.detailsConstraintLayout)
    }

    private fun replaceFragment(fragment: Fragment, args: Bundle) {
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
