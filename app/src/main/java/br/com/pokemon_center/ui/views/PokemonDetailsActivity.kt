package br.com.pokemon_center.ui.views

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import br.com.pokemon_center.commom.util.buttontypestyles.TypeStyle
import br.com.pokemon_center.commom.util.buttontypestyles.typesStyles
import br.com.pokemon_center.commom.util.capitalizedName
import br.com.pokemon_center.databinding.PokemonDetailsLayoutBinding
import br.com.pokemon_center.ui.viewmodels.PokemonDetailsViewModel
import coil.load
import com.google.android.material.button.MaterialButton

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
            if ( style != null) {
                applyTypeStyle(binding.type1, style)
            }
            binding.type1.text = capitalizedName(type)
        }

        viewModel.pokemonType2.observe(this) { type ->
            binding.type2.visibility = View.VISIBLE
            updateType1Constraints()

            val  style = typesStyles(type)
            if ( style != null) {
                applyTypeStyle(binding.type2, style)
            }
            binding.type2.text = capitalizedName(type)
        }
    }

    // Update the constraints based on the visibility of type2
    private fun updateType1Constraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)

        if (binding.type2.visibility == View.VISIBLE) {
            // If type2 is visible, constrain type1 to the start of the parent
             constraintSet.setHorizontalBias(binding.type1.id, 0.3f)
        } else {
            // If type2 is gone, constrain type1 to the center of the parent
             constraintSet.setHorizontalBias(binding.type1.id, 0.5f)
        }

        constraintSet.applyTo(binding.root)
    }

    // Apply the type style to the button
    private fun applyTypeStyle(button: MaterialButton, style: TypeStyle) {
        val color = ContextCompat.getColor(this, style.color)
        button.setIconResource(style.icon)
        button.setTextColor(color)
        button.strokeColor = ColorStateList.valueOf(color)
    }
}