package com.example.jetpack_compose_pokedex_app.repository

import com.example.jetpack_compose_pokedex_app.data.remote.PokeApi
import com.example.jetpack_compose_pokedex_app.data.remote.responses.Pokemon
import com.example.jetpack_compose_pokedex_app.data.remote.responses.PokemonList
import com.example.jetpack_compose_pokedex_app.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Created by Sri on 15,June,2023
 */
@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("\"An unknown error occured.\"")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(name)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}