package com.apps.rickandmortyappv2.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    fun getCharacters(@Query("page") page: Int): Call<CharacterList>
}

