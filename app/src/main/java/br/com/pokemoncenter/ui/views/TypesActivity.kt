package br.com.pokemoncenter.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.pokemon_center.databinding.ActivityTypesChartBinding
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView

class TypesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTypesChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypesChartBinding.inflate(layoutInflater)
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
