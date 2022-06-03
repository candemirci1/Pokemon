package com.example.pokemon.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.databinding.ItemPokemonBinding

class PokemonAdapter(
    private val pokemons: List<Pokemon>,
    private val onClick: (String) -> Unit
): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentPokemon = pokemons[position]
        holder.binding.apply {
            setName(tvPokemonName,currentPokemon)

            setImage(currentPokemon,this)

            root.setOnClickListener {
                onClick.invoke(currentPokemon.name)
            }
        }

    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    private fun setName(textview:TextView, currentPokemon: Pokemon) {
        textview.text = currentPokemon.name.replaceFirstChar {
            it.uppercase()
        }
    }

    private fun setImage(
        currentPokemon: Pokemon,
        binding: ItemPokemonBinding
    ) {
        val pokemonNumber = currentPokemon.url.getNumber()

        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$pokemonNumber.png"
        Glide.with(binding.root.context)
            .load(url)
            .into(binding.ivPokemon)
    }

}

fun String.getNumber(): String {
    return  if (this.endsWith("/")) {
        this.dropLast(1).takeLastWhile { it.isDigit() }
    } else {
        this.takeLastWhile { it.isDigit() }
    }


}