package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityMainBinding
import br.com.pokemoncenter.commom.util.hofs.textformat.removeBlankSpace

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mainSearchLayout.setEndIconOnClickListener {
            val pokemon = removeBlankSpace(binding.mainTextSearch.text.toString())
            val intent = Intent(this, PokemonDetailsActivity::class.java)
            intent.putExtra("pokemon", pokemon)
            startActivity(intent)
        }

        binding.mainTypesButton.setOnClickListener(this)
        binding.mainGenerationsButton.setOnClickListener(this)
        binding.mainNaturesButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.mainTypesButton.id -> {
                val intent = Intent(this, TypesActivity::class.java)
                startActivity(intent)
            }
            binding.mainGenerationsButton.id -> {
                val intent = Intent(this, GenerationsChoiceActivity::class.java)
                startActivity(intent)
            }
            binding.mainNaturesButton.id -> {
                val intent = Intent(this, NaturesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
