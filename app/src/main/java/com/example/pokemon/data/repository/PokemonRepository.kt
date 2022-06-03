package com.example.pokemon.data.repository

import com.example.pokemon.data.model.PokemonDTO
import com.example.pokemon.data.model.PokemonInfo
import com.example.pokemon.data.utils.Result

interface PokemonRepository {

    suspend fun getPokemons(): Result<PokemonDTO>
    suspend fun  getPokemonInfo(name:String): Result<PokemonInfo>
}

