package br.com.pokemoncenter.ui.views

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityPokemonDetailsBinding
import br.com.pokemoncenter.commom.util.hofs.textformat.capitalizedName
import br.com.pokemoncenter.commom.util.hofs.types.typesStyles
import br.com.pokemoncenter.ui.fragments.EffectivenessFragment
import br.com.pokemoncenter.ui.fragments.InfoFragment
import br.com.pokemoncenter.ui.fragments.MovesFragment
import br.com.pokemoncenter.ui.fragments.StatsFragment
import br.com.pokemoncenter.ui.viewmodels.PokemonDetailsViewModel
import br.com.pokemoncenter.ui.viewmodels.ViewModelFactory
import coil.load
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

        val fragment = InfoFragment()
        val bundlePokeName = Bundle()
        bundlePokeName.putString("pokemonName", pokemonName)

        val bundleTypes = Bundle()

        fragment.arguments = bundlePokeName

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()

        viewModel.pokemonId.observe(this) { id ->
            binding.id.text = String.format(Locale.ROOT, "#%03d", id)
        }

        viewModel.pokemonName.observe(this) { name ->
            binding.name.text = capitalizedName(name)
        }

        viewModel.pokemonImage.observe(this) { image ->
            binding.sprite.load(image)
        }

        viewModel.cries.observe(this) {
            cryUrl = it
        }

        viewModel.pokemonType1.observe(this) { type ->
            val style = typesStyles(type)
            bundleTypes.putString("type1", type)

            if (style != null) {
                applyTypeStyle(binding.type1, style)
            }
        }

        viewModel.pokemonType2.observe(this) { type ->
            binding.type2.visibility = View.VISIBLE
            updateType1Constraints()
            bundleTypes.putString("type2", type)

            val style = typesStyles(type)
            if (style != null) {
                applyTypeStyle(binding.type2, style)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    if (it) {
                        binding.detailsConstraintLayout.visibility = View.GONE
                        binding.detailsLinearLayout.visibility = View.GONE
                        binding.progressIndicator.visibility = View.VISIBLE
                        viewModel.errorMessage.observe(this@PokemonDetailsActivity) { message ->
                            Toast.makeText(this@PokemonDetailsActivity, message, Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                    } else {
                        binding.progressIndicator.visibility = View.GONE
                        binding.detailsConstraintLayout.visibility = View.VISIBLE
                        binding.detailsLinearLayout.visibility = View.VISIBLE
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

        binding.detailsTabLayout.getTabAt(0)?.view?.setOnClickListener {
            val infoFragment = InfoFragment()
            infoFragment.arguments = bundlePokeName

            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, infoFragment)
                .commit()
        }

        binding.detailsTabLayout.getTabAt(1)?.view?.setOnClickListener {
            val statsFragment = StatsFragment()
            statsFragment.arguments = bundlePokeName

            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, statsFragment)
                .commit()
        }

        binding.detailsTabLayout.getTabAt(2)?.view?.setOnClickListener {
            val effectivenessFragment = EffectivenessFragment()
            effectivenessFragment.arguments = bundleTypes

            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, effectivenessFragment)
                .commit()
        }

        binding.detailsTabLayout.getTabAt(3)?.view?.setOnClickListener {
            val movesFragment = MovesFragment()
            movesFragment.arguments = bundlePokeName

            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, movesFragment)
                .commit()
        }
    }

    // Update the constraints based on the visibility of type2
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

    private fun applyTypeStyle(image: ImageView, src: Int) {
        image.load(src)
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
