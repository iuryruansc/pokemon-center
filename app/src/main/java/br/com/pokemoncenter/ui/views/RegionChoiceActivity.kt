package br.com.pokemoncenter.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokemon_center.databinding.ActivityPokedexChoiceBinding
import br.com.pokemoncenter.commom.util.hofs.jsonparse.parseRegionsJson
import br.com.pokemoncenter.commom.util.hofs.textformat.capitalizedName
import br.com.pokemoncenter.commom.util.listeners.ChoiceListener
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView
import br.com.pokemoncenter.ui.adapters.RegionChoiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegionChoiceActivity : AppCompatActivity(), ChoiceListener {

    private lateinit var binding: ActivityPokedexChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.regionChoiceRv

        val json = assets.open("regions.json").bufferedReader().use { it.readText() }

        lifecycleScope.launch {
            val regions = withContext(Dispatchers.Default) {
                parseRegionsJson(json)
            }

            val adapter = RegionChoiceAdapter(regions)
            adapter.setChoiceListener(this@RegionChoiceActivity)

            recyclerView.layoutManager = LinearLayoutManager(this@RegionChoiceActivity)
            recyclerView.adapter = adapter
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener {
            menuItemClick(it, binding.drawerLayout)
        }
    }

    override fun onChoiceClick(view: View, position: Int) {
        val fileNames = listOf(
            ("pokedex/kanto.json"),
            "pokedex/johto.json",
            "pokedex/hoenn.json",
            "pokedex/sinnoh.json",
            "pokedex/unova.json",
            "pokedex/kalos.json",
            "pokedex/alola.json",
            "pokedex/galar.json",
            "pokedex/paldea.json",
            "pokedex/hisui.json"
        )
        if (position in fileNames.indices) {
            val regionName = fileNames[position].split("/").last().removeSuffix(".json")
            val json = assets.open(fileNames[position]).bufferedReader().use { it.readText() }
            genChose(json, regionName)
        }
    }

    private fun genChose(json: String, regionName: String) {
        val intent = Intent(this, RegionDetailsActivity::class.java)
        intent.putExtra("region", json)
        intent.putExtra("regionName", capitalizedName(regionName))
        startActivity(intent)
    }
}
