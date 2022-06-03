package com.example.pokemon.di

import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.data.repository.PokemonRepositoryImpl
import com.example.pokemon.data.service.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(service: PokemonService):PokemonRepository{
        return PokemonRepositoryImpl(service)
    }
}