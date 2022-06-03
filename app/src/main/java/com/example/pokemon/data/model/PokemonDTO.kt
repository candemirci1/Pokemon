package com.example.pokemon.data.model

data class PokemonDTO(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)
