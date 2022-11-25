package com.jig.UdemyReader.screens.details

import androidx.lifecycle.ViewModel
import com.jig.UdemyReader.data.Resource
import com.jig.UdemyReader.model.Item
import com.jig.UdemyReader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository)
    : ViewModel(){
        suspend fun getBookInfo(bookId: String): Resource<Item> {

            return repository.getBookInfo(bookId)
        }
}