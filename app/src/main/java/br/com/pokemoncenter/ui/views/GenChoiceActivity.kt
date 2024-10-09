package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.ActivityGenerationsChoiceBinding
import br.com.pokemoncenter.commom.util.generations.fileNames
import br.com.pokemoncenter.commom.util.generations.generationDetails
import br.com.pokemoncenter.commom.util.listeners.ChoiceListener
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView
import br.com.pokemoncenter.data.models.ButtonData
import br.com.pokemoncenter.ui.adapters.GenerationsChoiceAdapter

class GenChoiceActivity : AppCompatActivity(), ChoiceListener {

    private lateinit var binding: ActivityGenerationsChoiceBinding

    private val buttonDataList = listOf(
        ButtonData("Generation I", R.drawable.gen1_button),
        ButtonData("Generation II", R.drawable.gen2_button),
        ButtonData("Generation III", R.drawable.gen3_button),
        ButtonData("Generation IV", R.drawable.gen4_button),
        ButtonData("Generation V", R.drawable.gen5_button),
        ButtonData("Generation VI", R.drawable.gen6_button),
        ButtonData("Generation VII", R.drawable.gen7_button),
        ButtonData("Generation VIII", R.drawable.gen8_button),
        ButtonData("Generation IX", R.drawable.gen9_button)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerationsChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genRecycleView = binding.generationChoiceRv

        val adapter = GenerationsChoiceAdapter(buttonDataList)
        adapter.setChoiceListener(this)

        genRecycleView.layoutManager = LinearLayoutManager(this)
        genRecycleView.adapter = adapter

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener {
            menuItemClick(it, binding.drawerLayout)
        }
    }

    override fun onChoiceClick(view: View, position: Int) {
        if (position in 2..8) {
            val games = generationDetails(position)
            if (games != null) {
                gameSelection(games.toIntArray(), buttonDataList[position].text)
            }
        } else if (position in fileNames.indices){
            val json = assets.open(fileNames[position]).bufferedReader().use { it.readText() }
            genChose(json)
        }
    }

    private fun gameSelection(games: IntArray, genName: String) {
        val intent = Intent(this, GenGamesActivity::class.java)
        intent.putExtra("games", games)
        intent.putExtra("genName", genName)
        startActivity(intent)
    }

    private fun genChose(json: String) {
        val intent = Intent(this, GenDetailsActivity::class.java)
        intent.putExtra("sectionItems", json)
        startActivity(intent)
    }
}
