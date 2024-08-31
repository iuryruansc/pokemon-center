package br.com.pokemon_center.ui.views

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import br.com.pokemon_center.databinding.PokemonDetailsLayoutBinding
import br.com.pokemon_center.ui.viewmodels.PokemonDetailsViewModel
import coil.load

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: PokemonDetailsLayoutBinding
    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = PokemonDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra("pokemon")
        viewModel.pokemonDetails(pokemonName!!)

        viewModel.pokemonId.observe(this) { id ->
            binding.id.text = id.toString()
        }

        viewModel.pokemonName.observe(this) { name ->
            binding.name.text = name
        }

        viewModel.pokemonImage.observe(this) { image ->
            binding.sprite.load(image)
        }

        viewModel.pokemonType1.observe(this) { type ->
            binding.type1.text = type
        }

        viewModel.pokemonType2.observe(this) { type ->
            binding.type2.visibility = View.VISIBLE
            updateType1Constraints()
            binding.type2.text = type
        }
    }

    private fun updateType1Constraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)

        if (binding.type2.visibility == View.VISIBLE) {
            // If type2 is visible, constrain type1 to the start of the parent
             constraintSet.setHorizontalBias(binding.type1.id, 0.325f)
        } else {
            // If type2 is gone, constrain type1 to the center of the parent
             constraintSet.setHorizontalBias(binding.type1.id, 0.5f)
        }

        constraintSet.applyTo(binding.root)
    }
}