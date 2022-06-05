package com.example.pokemon.data.service

import com.example.pokemon.data.model.PokemonDTO
import com.example.pokemon.data.model.PokemonInfo
import okhttp3.internal.threadName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int = 60
    ): PokemonDTO

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(
        @Path("name") name: String
    ): PokemonInfo


}

