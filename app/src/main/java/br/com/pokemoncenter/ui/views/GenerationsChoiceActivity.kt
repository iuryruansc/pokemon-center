package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityGenerationsChoiceBinding
import br.com.pokemoncenter.commom.util.listeners.GenerationChoiceListener
import br.com.pokemoncenter.data.models.ButtonData
import br.com.pokemoncenter.ui.adapters.GenerationsChoiceAdapter

class GenerationsChoiceActivity : AppCompatActivity(), GenerationChoiceListener {

    private lateinit var binding: ActivityGenerationsChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerationsChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genRecycleView = binding.generationChoiceRv

        val buttonDataList = listOf(
            ButtonData("Generation 1", R.drawable.gen1_button),
            ButtonData("Generation 2", R.drawable.gen2_button),
            ButtonData("Generation 3", R.drawable.gen3_button),
            ButtonData("Generation 4", R.drawable.gen4_button),
            ButtonData("Generation 5", R.drawable.gen5_button),
            ButtonData("Generation 6", R.drawable.gen6_button),
            ButtonData("Generation 7", R.drawable.gen7_button),
            ButtonData("Generation 8", R.drawable.gen8_button),
            ButtonData("Generation 9", R.drawable.gen9_button)
        )

        val adapter = GenerationsChoiceAdapter(buttonDataList)
        adapter.setChoiceListener(this)

        genRecycleView.layoutManager = LinearLayoutManager(this)
        genRecycleView.adapter = adapter
    }

    override fun onChoiceClick(view: View, position: Int) {
        val fileNames = listOf(
            "generation_1.json",
            "generation_2.json",
            "generation_3.json",
            "generation_4.json",
            "generation_5.json",
            "generation_6.json",
            "generation_7.json",
            "generation_8.json",
            "generation_9.json"
        )
        if (position in fileNames.indices) {
            val json = assets.open(fileNames[position]).bufferedReader().use { it.readText() }
            genChose(json)
        }
    }

    private fun genChose(json: String) {
        val intent = Intent(this, GenerationsDetailsActivity::class.java)
        intent.putExtra("sectionItems", json)
        startActivity(intent)
    }
}
