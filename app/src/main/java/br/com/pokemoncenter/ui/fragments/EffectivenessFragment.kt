package br.com.pokemoncenter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokemon_center.R
import br.com.pokemon_center.databinding.FragmentEffectivenessBinding
import br.com.pokemoncenter.commom.util.hofs.general.createBalloon
import br.com.pokemoncenter.commom.util.hofs.general.readJson
import br.com.pokemoncenter.commom.util.hofs.types.getCombinedTypeEffectivenessDef
import br.com.pokemoncenter.commom.util.hofs.types.parseTypeEffectiveness
import br.com.pokemoncenter.commom.util.listeners.FragmentEffectListener
import br.com.pokemoncenter.ui.adapters.TypeEffectivenessAdapter

class EffectivenessFragment : Fragment(), FragmentEffectListener {

    private var _binding: FragmentEffectivenessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEffectivenessBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.effectivenessContainer.visibility = View.GONE
        binding.effectivenessLoading.visibility = View.VISIBLE

        val jsonString = readJson(requireContext(), "all_types.json")
        val type1 = arguments?.getString("type1")
        val type2 = arguments?.getString("type2")
        val defenseRecyclerView = binding.rvDefenseEffectiveness

        if (jsonString != null) {
            val typeEffectivenessMap = parseTypeEffectiveness(jsonString)

            val combinedDefenseEffectiveness = if (type1 != null && type2 != null) {
                getCombinedTypeEffectivenessDef(type1, type2, typeEffectivenessMap)
            } else if (type1 != null) {
                getCombinedTypeEffectivenessDef(type1, null, typeEffectivenessMap)
            } else {
                emptyMap()
            }

            val filteredDefEffectiveness = combinedDefenseEffectiveness.filter { it.value != 1.0 }

            val adapter = TypeEffectivenessAdapter(filteredDefEffectiveness)
            adapter.setEffectListener(this)

            defenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            defenseRecyclerView.adapter = adapter
        } else {
            // TODO handle null json
        }

        binding.effectivenessContainer.visibility = View.VISIBLE
        binding.effectivenessLoading.visibility = View.GONE
    }

    override fun onItemClick(view: View, position: Int, mult: CharSequence) {
        var text = ""
        var color = 0

        when (mult) {
            "x4.0" -> {
                text = "This pokémon takes 4x damage from this type."
                color = R.color.quadrupleEffective
            }

            "x2.0" -> {
                text = "This pokémon takes double damage from this type."
                color = R.color.theme_dark_double_effective
            }

            "x0.5" -> {
                text = "This pokémon takes half damage from this type."
                color = R.color.doubleResist
            }

            "x0.25" -> {
                text = "This pokémon takes 0.25x damage from this type."
                color = R.color.quadrupleResist
            }

            "x0.0" -> {
                text = "This type has no effect on this pokémon."
                color = R.color.noEffect
            }
        }

        val balloon = createBalloon(requireContext(), this, text, color)

        balloon.showAlignTop(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
