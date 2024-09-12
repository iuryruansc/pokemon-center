package br.com.pokemoncenter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.pokemon_center.databinding.FragmentStatsBinding
import br.com.pokemoncenter.commom.Constants.MAX_LEVEL
import br.com.pokemoncenter.commom.Constants.MIN_LEVEL
import br.com.pokemoncenter.commom.StatIndex
import br.com.pokemoncenter.commom.util.hofs.general.calculateStatAtLevel
import br.com.pokemoncenter.ui.viewmodels.StatsFragmentViewModel

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
        showLoading()

        arguments?.getString("pokemonName")?.let { pokemonName ->
            viewModel.pokemonStats(pokemonName)
            observeViewModel()
            setupCalculateButtonListener()
        }
    }

    private fun observeViewModel() {
        viewModel.stats.observe(viewLifecycleOwner) {
            with(binding) {
                statValueHp.text = it[StatIndex.HP.ordinal].baseStat.toString()
                statProgressHp.progress = it[StatIndex.HP.ordinal].baseStat
                statValueAtk.text = it[StatIndex.ATK.ordinal].baseStat.toString()
                statProgressAtk.progress = it[StatIndex.ATK.ordinal].baseStat
                statValueDef.text = it[StatIndex.DEF.ordinal].baseStat.toString()
                statProgressDef.progress = it[StatIndex.DEF.ordinal].baseStat
                statValueSatk.text = it[StatIndex.SATK.ordinal].baseStat.toString()
                statProgressSatk.progress = it[StatIndex.SATK.ordinal].baseStat
                statValueSdef.text = it[StatIndex.SDEF.ordinal].baseStat.toString()
                statProgressSdef.progress = it[StatIndex.SDEF.ordinal].baseStat
                statValueSpeed.text = it[StatIndex.SPEED.ordinal].baseStat.toString()
                statProgressSpeed.progress = it[StatIndex.SPEED.ordinal].baseStat
                hideLoading()
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showError(message)
        }
    }

    private fun setupCalculateButtonListener() {
        binding.calculateStatsButton.setOnClickListener {
            val level = binding.levelInput.text.toString().toIntOrNull()
            if (level in MIN_LEVEL..MAX_LEVEL) {
                calculateStatsAtLevel(level!!)
            } else {
                showLevelError()
            }
        }
    }

    private fun calculateStatsAtLevel(level: Int) {
        with(binding) {
            val hp = calculateStatAtLevel(statValueHp.text.toString().toInt(), level)
            hpAtLevel.text = hp.toString()
            hpAtLevel.visibility = View.VISIBLE

            val atk = calculateStatAtLevel(statValueAtk.text.toString().toInt(), level)
            atkAtLvl.text = atk.toString()
            atkAtLvl.visibility = View.VISIBLE

            val def = calculateStatAtLevel(statValueDef.text.toString().toInt(), level)
            defAtLvl.text = def.toString()
            defAtLvl.visibility = View.VISIBLE

            val satk = calculateStatAtLevel(statValueSatk.text.toString().toInt(), level)
            satkAtLvl.text = satk.toString()
            satkAtLvl.visibility = View.VISIBLE

            val sdef = calculateStatAtLevel(statValueSdef.text.toString().toInt(), level)
            sdefAtLvl.text = sdef.toString()
            sdefAtLvl.visibility = View.VISIBLE

            val spd = calculateStatAtLevel(statValueSpeed.text.toString().toInt(), level)
            spdAtLvl.text = spd.toString()
            spdAtLvl.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.linearContainer.visibility = View.GONE
        binding.statsLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.linearContainer.visibility = View.VISIBLE
        binding.statsLoading.visibility = View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLevelError() {
        Toast.makeText(
            context,
            "Level must be between 1 and 99",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
