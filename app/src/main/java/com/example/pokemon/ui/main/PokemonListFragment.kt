package com.example.pokemon.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val viewModel: PokemonListViewModel by viewModels()

    private var pokemonAdapter: PokemonAdapter? = null

    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is PokemonListViewState.Success -> {
                        binding?.loading?.isVisible = false
                        it.data?.let { pokemonDTO ->

                            pokemonAdapter = PokemonAdapter(pokemonDTO.results) { pokemonName ->
                                val action =
                                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonInfoFragment(
                                        pokemonName
                                    )
                                findNavController().navigate(action)
                            }
                            binding?.rvPokemon?.layoutManager = GridLayoutManager(requireContext(),3)
                            binding?.rvPokemon?.adapter = pokemonAdapter
                        }


                    }
                    is PokemonListViewState.Error -> {
                        binding?.loading?.isVisible = false
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    is PokemonListViewState.Loading -> {
                        binding?.loading?.isVisible = true
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
