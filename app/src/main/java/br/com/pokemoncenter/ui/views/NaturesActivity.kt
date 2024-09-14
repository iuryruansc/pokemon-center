package br.com.pokemoncenter.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.pokemon_center.databinding.ActivityNaturesBinding
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView

class NaturesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNaturesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener {
            menuItemClick(it, binding.drawerLayout)
        }
    }
}
