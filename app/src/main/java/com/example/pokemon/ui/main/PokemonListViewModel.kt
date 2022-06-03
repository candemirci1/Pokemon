package com.example.pokemon.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.model.PokemonDTO
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    val state = MutableStateFlow<PokemonListViewState>(PokemonListViewState.Loading)
    init {
        getPokemons()
    }

    private fun getPokemons() {
        viewModelScope.launch {
           pokemonRepository.getPokemons().let {
                when(it) {
                    is Result.Loading -> state.value = PokemonListViewState.Loading
                    is Result.Success -> state.value = PokemonListViewState.Success(it.data)
                    is Result.Error -> state.value = PokemonListViewState.Error(it.message.orEmpty())
                }
            }
        }
    }

}


sealed class PokemonListViewState {
    object Loading : PokemonListViewState()

    data class Success(
        val data: PokemonDTO?
    ): PokemonListViewState()

    data class Error(
        val message: String
    ) : PokemonListViewState()

}