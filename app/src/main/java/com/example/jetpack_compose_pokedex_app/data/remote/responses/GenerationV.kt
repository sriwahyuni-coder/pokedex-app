package com.example.jetpack_compose_pokedex_app.data.remote.responses


import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val blackWhite: BlackWhite
)