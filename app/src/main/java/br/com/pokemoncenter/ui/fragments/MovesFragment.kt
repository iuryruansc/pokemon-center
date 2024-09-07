package br.com.pokemoncenter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import br.com.pokemon_center.databinding.FragmentMovesBinding
import br.com.pokemoncenter.ui.adapters.MoveAdapter
import br.com.pokemoncenter.ui.viewmodels.MovesFragmentViewModel
import kotlinx.coroutines.launch

class MovesFragment : Fragment() {

    private val viewModel: MovesFragmentViewModel by viewModels()
    private var _binding: FragmentMovesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonName = arguments?.getString("pokemonName")

        viewModel.pokemonMoves(pokemonName!!)
        val recyclerView = binding.rvMoves

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moveObjects.collect { moveList ->
                val adapter = MoveAdapter(moveList)

                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
