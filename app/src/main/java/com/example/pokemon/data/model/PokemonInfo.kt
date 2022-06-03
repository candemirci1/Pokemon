package com.example.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonInfo(
    val abilities: List<AbilityInfo>,
    val height: Int,
    val weight: Int,
    val name: String,
    val sprites: Sprites
)

data class AbilityInfo(
    val ability: Ability
)


data class Ability(
    val name: String,
    val url: String
)

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String

)
