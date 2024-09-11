package br.com.pokemoncenter.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.pokemon_center.databinding.ActivityGenerationsDetailsBinding
import br.com.pokemoncenter.commom.util.hofs.jsonparse.parseGenerationJson
import br.com.pokemoncenter.ui.adapters.SectionAdapter

class GenerationsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerationsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerationsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = intent.getStringExtra("sectionItems") ?: return

        val sectionItems = parseGenerationJson(jsonString)

        val adapter = SectionAdapter()
        adapter.setItems(sectionItems)

        binding.generationRv.adapter = adapter
    }
}
