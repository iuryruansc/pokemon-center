package br.com.pokemon_center.ui.views

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.pokemon_center.commom.util.buttontypestyles.TypeStyle
import br.com.pokemon_center.commom.util.buttontypestyles.typesStyles
import br.com.pokemon_center.commom.util.capitalizedName
import br.com.pokemon_center.databinding.ActivityPokemonDetailsBinding
import br.com.pokemon_center.ui.fragments.InfoFragment
import br.com.pokemon_center.ui.fragments.StatsFragment
import br.com.pokemon_center.ui.viewmodels.PokemonDetailsViewModel
import coil.load
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra("pokemon")
        viewModel.pokemonDetails(pokemonName!!)

        val fragment = InfoFragment()
        val bundle = Bundle()
        bundle.putString("pokemonName", pokemonName)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()

        viewModel.pokemonId.observe(this) { id ->
            binding.id.text = "#$id"
        }

        viewModel.pokemonName.observe(this) { name ->
            binding.name.text = capitalizedName(name)
        }

        viewModel.pokemonImage.observe(this) { image ->
            binding.sprite.load(image)
        }

        viewModel.pokemonType1.observe(this) { type ->
            val style = typesStyles(type)
            if (style != null) {
                applyTypeStyle(binding.type1, style)
            }
            binding.type1.text = capitalizedName(type)
        }

        viewModel.pokemonType2.observe(this) { type ->
            binding.type2.visibility = View.VISIBLE
            updateType1Constraints()

            val style = typesStyles(type)
            if (style != null) {
                applyTypeStyle(binding.type2, style)
            }
            binding.type2.text = capitalizedName(type)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    if (it) {
                        binding.detailsConstraintLayout.visibility = View.GONE
                        binding.detailsLinearLayout.visibility = View.GONE
                        binding.progressIndicator.visibility = View.VISIBLE
                    } else {
                        binding.progressIndicator.visibility = View.GONE
                        binding.detailsConstraintLayout.visibility = View.VISIBLE
                        binding.detailsLinearLayout.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.detailsTabLayout.getTabAt(0)?.view?.setOnClickListener {
            val infoFragment = InfoFragment()
            infoFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, infoFragment)
                .commit()
        }

        binding.detailsTabLayout.getTabAt(1)?.view?.setOnClickListener {
            val statsFragment = StatsFragment()
            statsFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, statsFragment)
                .commit()
        }
    }

    // Update the constraints based on the visibility of type2
    private fun updateType1Constraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.detailsConstraintLayout)

        if (binding.type2.visibility == View.VISIBLE) {
            // If type2 is visible, constrain type1 to the start of the parent
            constraintSet.setHorizontalBias(binding.type1.id, 0.275f)
        } else {
            // If type2 is gone, constrain type1 to the center of the parent
            constraintSet.setHorizontalBias(binding.type1.id, 0.5f)
        }
        constraintSet.applyTo(binding.detailsConstraintLayout)
    }

    // Apply the type style to the button
    private fun applyTypeStyle(button: MaterialButton, style: TypeStyle) {
        val color = ContextCompat.getColor(this, style.color)
        button.setIconResource(style.icon)
        button.setTextColor(color)
        button.strokeColor = ColorStateList.valueOf(color)
    }
}
