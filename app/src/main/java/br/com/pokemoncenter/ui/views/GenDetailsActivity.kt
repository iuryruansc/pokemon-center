package br.com.pokemoncenter.ui.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.pokemon_center.databinding.ActivityGenerationsDetailsBinding
import br.com.pokemoncenter.commom.util.hofs.jsonparse.parseGenerationJson
import br.com.pokemoncenter.commom.util.ui.menuItemClick
import br.com.pokemoncenter.commom.util.ui.setupNavigationView
import br.com.pokemoncenter.ui.adapters.SectionAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerationsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerationsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading()

        val jsonString = intent.getStringExtra("sectionItems") ?: return

        lifecycleScope.launch {
            val sectionItems = withContext(Dispatchers.Default) {
                parseGenerationJson(jsonString)
            }
            val adapter = SectionAdapter()
            adapter.setItems(sectionItems)

            binding.generationRv.adapter = adapter

            hideLoading()
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupNavigationView(binding.navigationView, binding.drawerLayout)

        binding.topAppBar.setOnMenuItemClickListener {
            menuItemClick(it, binding.drawerLayout)
        }
    }

    private fun showLoading() {
        binding.generationRv.visibility = View.GONE
        binding.genDetailsLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.genDetailsLoading.visibility = View.GONE
        binding.generationRv.visibility = View.VISIBLE
    }
}
