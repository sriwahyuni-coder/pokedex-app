package com.example.jetpack_compose_pokedex_app.data.remote

import com.example.jetpack_compose_pokedex_app.data.remote.responses.Pokemon
import com.example.jetpack_compose_pokedex_app.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Sri on 15,June,2023
 */
interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ) : Pokemon
}