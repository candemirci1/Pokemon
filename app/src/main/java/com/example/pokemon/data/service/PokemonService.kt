package com.example.pokemon.data.service

import com.example.pokemon.data.model.PokemonDTO
import com.example.pokemon.data.model.PokemonInfo
import okhttp3.internal.threadName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon")
    suspend fun fetchPokemons(): PokemonDTO

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(
        @Path("name") name: String
    ): PokemonInfo
}

