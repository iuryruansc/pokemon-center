package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokemon_center.databinding.ActivityGenGamesBinding
import br.com.pokemoncenter.commom.util.generations.getGenerationJson
import br.com.pokemoncenter.commom.util.listeners.ChoiceListener
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView
import br.com.pokemoncenter.ui.adapters.GenGamesAdapter

class GenGamesActivity : AppCompatActivity(), ChoiceListener {

    private lateinit var binding: ActivityGenGamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genGamesRv = binding.genGameRv
        val games = intent.getIntArrayExtra("games")
        val genName = intent.getStringExtra("genName")

        if (games != null) {

            val adapter = GenGamesAdapter(games)
            adapter.setChoiceListener(this)

            genGamesRv.layoutManager = LinearLayoutManager(this)
            genGamesRv.adapter = adapter
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.topAppBar.title = genName

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener {
            menuItemClick(it, binding.drawerLayout)
        }
    }

    override fun onChoiceClick(view: View, position: Int) {
        val gamesArray = intent.getIntArrayExtra("games")!!
        val logoID = gamesArray[position]

        val jsonPath = getGenerationJson(logoID)
        if (jsonPath != null) {
            loadGameJson(jsonPath)
        }
    }

    private fun loadGameJson(filePath: String) {
        val json = assets.open(filePath).bufferedReader().use { it.readText() }
        gameChose(json)
    }

    private fun gameChose(json: String) {
        val intent = Intent(this, GenDetailsActivity::class.java)
        intent.putExtra("sectionItems", json)
        startActivity(intent)
    }
}
