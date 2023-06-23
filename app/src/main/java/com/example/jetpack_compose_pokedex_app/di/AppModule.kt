package com.example.jetpack_compose_pokedex_app.di

import com.example.jetpack_compose_pokedex_app.data.remote.PokeApi
import com.example.jetpack_compose_pokedex_app.repository.PokemonRepository
import com.example.jetpack_compose_pokedex_app.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Sri on 15,June,2023
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providerPokemonRepository(api : PokeApi) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providerPokeApi() : PokeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

}