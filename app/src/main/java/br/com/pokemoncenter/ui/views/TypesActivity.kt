package br.com.pokemoncenter.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.pokemon_center.databinding.ActivityTypesChartBinding

class TypesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTypesChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypesChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
