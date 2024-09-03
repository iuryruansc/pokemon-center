package br.com.pokemon_center.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.pokemon_center.commom.util.hofs.textformat.capitalizedName
import br.com.pokemon_center.databinding.FragmentInfoBinding
import br.com.pokemon_center.ui.viewmodels.InfoFragmentViewModel

class InfoFragment : Fragment() {

    private val viewModel: InfoFragmentViewModel by viewModels()
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private var cryUrl: String? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonName = arguments?.getString("pokemonName")
        if (pokemonName != null) {
            viewModel.pokemonSpecies(pokemonName)
            viewModel.pokemonDetails(pokemonName)

            viewModel.pokemonDescription.observe(viewLifecycleOwner) {
                binding.infoDescription.text = it
            }

            viewModel.pokemonHeight.observe(viewLifecycleOwner) {
                binding.heightValue.text = it
            }

            viewModel.pokemonWeight.observe(viewLifecycleOwner) {
                binding.weightValue.text = it
            }

            viewModel.captureRate.observe(viewLifecycleOwner) {
                binding.captureRateValue.text = it
                binding.pokeballRate.progress = it.toInt()
                binding.greatballRate.progress = it.toInt()
                binding.ultraballRate.progress = it.toInt()
            }

            viewModel.baseExperience.observe(viewLifecycleOwner) {
                binding.baseExperienceValue.text = it
            }

            viewModel.genera.observe(viewLifecycleOwner) {
                binding.generaValue.text = it
            }

            viewModel.generationDebut.observe(viewLifecycleOwner) {
                binding.generationText.text = capitalizedName(it)
            }

            viewModel.cries.observe(viewLifecycleOwner) {
                cryUrl = it
            }

            binding.cryPlayButton.setOnClickListener {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                } else {
                    cryUrl?.let { url ->
                        mediaPlayer = MediaPlayer()
                            .apply {
                                setDataSource(url)
                                prepareAsync()
                                setOnPreparedListener {
                                    start()
                                }
                            }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
