package com.example.pokemon.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokemon.data.model.PokemonInfo
import com.example.pokemon.databinding.FragmentPokemonInfoBinding
import com.example.pokemon.ui.main.PokemonAdapter
import com.example.pokemon.ui.main.PokemonListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_info.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonInfoFragment: Fragment() {

    private val viewModel: PokemonInfoViewModel by viewModels()

    private var binding: FragmentPokemonInfoBinding? = null

    private val args: PokemonInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemonInfo(args.name)
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is PokemonInfoViewState.Success -> {
                        binding?.loading?.isVisible = false
                        it.data?.let { pokemonInfo ->

                            binding?.apply {
                                Glide.with(requireContext())
                                    .load(pokemonInfo.sprites.backDefault)
                                    .into(ivPokemonImage)
                                setPokemonInfo(tvPokemonName,pokemonInfo.name)
                                setPokemonInfo(tvPokemonHeight,pokemonInfo.height.toString())
                                setPokemonInfo(tvPokemonWeight,pokemonInfo.weight.toString())
                                setPokemonInfo(tvPokemonAbility,setAbilities(pokemonInfo.abilities.map { it.ability.name }))

                            }


                        }


                    }
                    is PokemonInfoViewState.Error -> {
                        binding?.loading?.isVisible = false
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    is PokemonInfoViewState.Loading -> {
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

    private fun setAbilities(abilities: List<String>): String {
        var text =""
        for (ability in abilities) {
            text += "$ability\n"
        }
        return text
    }

    private fun setPokemonInfo(textView: TextView, text: String) {
        textView.text = text

    }
}