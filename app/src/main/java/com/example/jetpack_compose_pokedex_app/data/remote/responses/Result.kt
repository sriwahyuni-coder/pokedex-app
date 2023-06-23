package com.example.jetpack_compose_pokedex_app.data.remote.responses


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)