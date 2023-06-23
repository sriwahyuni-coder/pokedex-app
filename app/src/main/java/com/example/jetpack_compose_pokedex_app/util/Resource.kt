package com.example.jetpack_compose_pokedex_app.util

/**
 * Created by Sri on 15,June,2023
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
