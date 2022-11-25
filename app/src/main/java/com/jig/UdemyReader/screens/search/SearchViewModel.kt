package com.jig.UdemyReader.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jig.UdemyReader.data.Resource
import com.jig.UdemyReader.model.Item
import com.jig.UdemyReader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(private val repository: BookRepository): ViewModel(){

    var listOfBooks: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks(){
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                return@launch
            }
            try {
                when(val response = repository.getBooks(query)){
                    is Resource.Success -> {
                        listOfBooks = response.data!!
                        if(listOfBooks.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error -> {
                        Log.e("SearchError", "searchBooks: Failed to get books", )
                    }
                    else->{isLoading = false}
                }

            } catch (exception: Exception){
                isLoading = false
                Log.d("search errror", "searchBooks: ${exception.message.toString()}")

            }
        }

    }
}