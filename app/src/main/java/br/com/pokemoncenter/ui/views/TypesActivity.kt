package br.com.pokemoncenter.ui.views

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.pokemon_center.databinding.ActivityTypesChartBinding

class TypesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTypesChartBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        enableEdgeToEdge()
        binding = ActivityTypesChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
