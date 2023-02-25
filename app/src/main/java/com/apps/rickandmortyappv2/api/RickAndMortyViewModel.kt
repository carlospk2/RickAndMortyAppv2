package com.apps.rickandmortyappv2.api

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyViewModel : ViewModel() {

    private var apiInterface: RickAndMortyApiService
    private lateinit var charactersCall: Call<CharacterList>
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    val state = mutableStateOf(emptyList<Character>())

    //Pagination
    var currentPage = mutableStateOf(1)
    var maxPages = mutableStateOf(0)

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()
        apiInterface = retrofit.create(RickAndMortyApiService::class.java)
        getCharacters(currentPage.value)
    }

    //compare an Int that is from an id to id in list of character
    fun getCharacterById(id: Int): Character? {
        return state.value.find { it.id == id }
    }

    fun loadPreviousPage() {
        if (currentPage.value == 1) return
        getPreviousPage()?.let {
            getCharacters(it)
        }
    }

    fun loadNextPage() {
        if (currentPage.value == maxPages.value) return
        getNextPage()?.let {
            getCharacters(it)
        } ?: run {
        }
    }

    private fun getPreviousPage(): Int? {
        currentPage.value = currentPage.value - 1
        return if (currentPage.value in 1..maxPages.value) currentPage.value else null
    }

    private fun getNextPage(): Int? {
        currentPage.value = currentPage.value + 1
        return if (currentPage.value in 1..maxPages.value) currentPage.value else null
    }

    private fun getCharacters(page: Int) {
        charactersCall = apiInterface.getCharacters(page)
        charactersCall.enqueue(
            object : Callback<CharacterList> {
                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    response.body()?.let { characterList ->
                        state.value = characterList.results
                        //currentPage.value = if (page in 1..maxPages.value) page else 1
                        maxPages.value = characterList.info.pages
                    }
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        charactersCall.cancel()
    }

}
