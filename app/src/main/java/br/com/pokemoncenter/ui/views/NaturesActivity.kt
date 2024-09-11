package br.com.pokemoncenter.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.pokemon_center.databinding.ActivityNaturesBinding

class NaturesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNaturesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaturesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
