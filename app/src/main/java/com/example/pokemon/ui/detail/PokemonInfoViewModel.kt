package com.example.pokemon.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.model.PokemonInfo
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    val state = MutableStateFlow<PokemonInfoViewState>(PokemonInfoViewState.Loading)


    fun getPokemonInfo(name: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemonInfo(name).let {
                when(it) {
                    is Result.Loading -> state.value = PokemonInfoViewState.Loading
                    is Result.Success -> state.value = PokemonInfoViewState.Success(it.data)
                    is Result.Error -> state.value = PokemonInfoViewState.Error(it.message.orEmpty())
                }
            }
        }
    }

}


sealed class PokemonInfoViewState {
    object Loading : PokemonInfoViewState()

    data class Success(
        val data: PokemonInfo?
    ): PokemonInfoViewState()

    data class Error(
        val message: String
    ) : PokemonInfoViewState()

}