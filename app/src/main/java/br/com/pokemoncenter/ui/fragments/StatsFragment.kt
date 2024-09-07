package br.com.pokemoncenter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.pokemon_center.databinding.FragmentStatsBinding
import br.com.pokemoncenter.commom.util.hofs.general.calculateStatAtLevel
import br.com.pokemoncenter.ui.viewmodels.StatsFragmentViewModel
import kotlinx.coroutines.launch

class StatsFragment : Fragment() {

    private val viewModel: StatsFragmentViewModel by viewModels()
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.linearContainer.visibility = View.GONE
        binding.statsLoading.visibility = View.VISIBLE

        val pokemonName = arguments?.getString("pokemonName")
        if (pokemonName != null) {
            viewModel.pokemonStats(pokemonName)

            viewModel.stats.observe(viewLifecycleOwner) {
                binding.statValueHp.text = it[0].baseStat.toString()
                binding.statProgressHp.progress = it[0].baseStat
                binding.statValueAtk.text = it[1].baseStat.toString()
                binding.statProgressAtk.progress = it[1].baseStat
                binding.statValueDef.text = it[2].baseStat.toString()
                binding.statProgressDef.progress = it[2].baseStat
                binding.statValueSatk.text = it[3].baseStat.toString()
                binding.statProgressSatk.progress = it[3].baseStat
                binding.statValueSdef.text = it[4].baseStat.toString()
                binding.statProgressSdef.progress = it[4].baseStat
                binding.statValueSpeed.text = it[5].baseStat.toString()
                binding.statProgressSpeed.progress = it[5].baseStat
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    if (it) {
                        binding.statsLoading.visibility = View.VISIBLE
                        binding.linearContainer.visibility = View.GONE
                        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        binding.statsLoading.visibility = View.GONE
                        binding.linearContainer.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.calculateStatsButton.setOnClickListener {
            val level = binding.levelInput.text.toString().toInt()
            if (level in 1..99) {
                val hp = calculateStatAtLevel(binding.statValueHp.text.toString().toInt(), level)
                binding.hpAtLevel.text = hp.toString()
                binding.hpAtLevel.visibility = View.VISIBLE

                val atk = calculateStatAtLevel(binding.statValueAtk.text.toString().toInt(), level)
                binding.atkAtLvl.text = atk.toString()
                binding.atkAtLvl.visibility = View.VISIBLE

                val def = calculateStatAtLevel(binding.statValueDef.text.toString().toInt(), level)
                binding.defAtLvl.text = def.toString()
                binding.defAtLvl.visibility = View.VISIBLE

                val satk =
                    calculateStatAtLevel(binding.statValueSatk.text.toString().toInt(), level)
                binding.satkAtLvl.text = satk.toString()
                binding.satkAtLvl.visibility = View.VISIBLE

                val sdef =
                    calculateStatAtLevel(binding.statValueSdef.text.toString().toInt(), level)
                binding.sdefAtLvl.text = sdef.toString()
                binding.sdefAtLvl.visibility = View.VISIBLE

                val spd =
                    calculateStatAtLevel(binding.statValueSpeed.text.toString().toInt(), level)
                binding.spdAtLvl.text = spd.toString()
                binding.spdAtLvl.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    this@StatsFragment.context,
                    "Level must be between 1 and 99",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
