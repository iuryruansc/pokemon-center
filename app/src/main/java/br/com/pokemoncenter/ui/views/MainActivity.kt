package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityMainBinding
import br.com.pokemoncenter.commom.util.hofs.textformat.removeBlankSpace
import br.com.pokemoncenter.commom.util.ui.showExitConfirmationDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var isFirstView = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mainSearchLayout.isEndIconVisible = false

        binding.mainTextSearch.doAfterTextChanged { editable ->
            val text = editable?.toString()?.trim() ?: ""
            binding.mainSearchLayout.isEndIconVisible = text.isNotEmpty()
        }

        binding.mainSearchLayout.setEndIconOnClickListener {
            val pokemon = removeBlankSpace(binding.mainTextSearch.text.toString())
            val intent = Intent(this, PokemonDetailsActivity::class.java)
            intent.putExtra("pokemon", pokemon)
            startActivity(intent)
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("MainActivity", "handleOnBackPressed: $isFirstView")
                    if (isFirstView) {
                        showExitConfirmationDialog(this@MainActivity)
                    } else {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        )

        binding.mainTypesButton.setOnClickListener(this)
        binding.mainGenerationsButton.setOnClickListener(this)
        binding.mainNaturesButton.setOnClickListener(this)
        binding.mainFavoritesButton?.setOnClickListener(this)
        binding.mainPokemonButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.mainTypesButton.id -> startActivity(Intent(this, TypesActivity::class.java))

            binding.mainGenerationsButton.id -> startActivity(Intent(this, GenChoiceActivity::class.java))

            binding.mainNaturesButton.id -> startActivity(Intent(this, NaturesActivity::class.java))

            binding.mainFavoritesButton?.id -> startActivity(Intent(this, FavoritesActivity::class.java))

            binding.mainPokemonButton.id -> startActivity(Intent(this, RegionChoiceActivity::class.java))
        }
    }
}
