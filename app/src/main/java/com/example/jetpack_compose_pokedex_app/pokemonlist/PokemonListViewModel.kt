package com.example.jetpack_compose_pokedex_app.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.jetpack_compose_pokedex_app.data.model.PokedexListEntry
import com.example.jetpack_compose_pokedex_app.repository.PokemonRepository
import com.example.jetpack_compose_pokedex_app.util.Constants.PAGE_SIZE
import com.example.jetpack_compose_pokedex_app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Sri on 19,June,2023
 */

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private var currPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, currPage * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number =
                            if (entry.url.endsWith("/")) {   // endWith -> cek karakter terakhir
                                entry.url.dropLast(1)
                                    .takeLastWhile { it.isDigit() }  //dropLast -> hapus n karakter terakhir
                            } else {
                                entry.url.takeLastWhile { it.isDigit() }
                            }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(
                            entry.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(java.util.Locale.ROOT) else it.toString() },
                            url,
                            number.toInt()
                        )
                    }
                    currPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }

    }


    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bpm = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bpm).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                Log.e("BPM", colorValue.toString())
                onFinish(Color(colorValue))
            }
        }
    }
}