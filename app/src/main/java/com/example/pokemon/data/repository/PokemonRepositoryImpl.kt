package com.example.pokemon.data.repository

import com.example.pokemon.data.model.PokemonDTO
import com.example.pokemon.data.model.PokemonInfo
import com.example.pokemon.data.service.PokemonService
import com.example.pokemon.data.utils.Result
import retrofit2.HttpException
import java.io.IOException

class PokemonRepositoryImpl(private val service: PokemonService) : PokemonRepository {

    override suspend fun getPokemons(): Result<PokemonDTO> {

        return try {
            val pokemons = service.fetchPokemons()
            Result.Success(pokemons)
        } catch (e: HttpException) {
            Result.Error(e.message.orEmpty(), e.code())
        } catch (e: IOException) {
            Result.Error("check your internet connection")
        }
    }

    override suspend fun getPokemonInfo(name:String): Result<PokemonInfo> {

        return try {
            val pokemonInfo = service.fetchPokemonInfo(name)
            Result.Success(pokemonInfo)
        } catch (e: HttpException) {
            Result.Error(e.message.orEmpty(), e.code())
        } catch (e: IOException) {
            Result.Error("check your internet connection")
        }

    }
}