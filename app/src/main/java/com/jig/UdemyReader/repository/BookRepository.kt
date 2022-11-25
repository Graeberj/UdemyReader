package com.jig.UdemyReader.repository

import com.jig.UdemyReader.data.Resource
import com.jig.UdemyReader.model.Item
import com.jig.UdemyReader.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {

    suspend fun getBooks(query: String): Resource<List<Item>>{
        return try {
            Resource.Loading(data = true)
            val itemList = api.getAllBooks(query).items
            if(itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (e: Exception){
            Resource.Error(message = e.message.toString())
        }

    }
    suspend fun getBookInfo(bookId: String): Resource<Item>{
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)
        } catch(exception: Exception){
            return Resource.Error(message = exception.message.toString())
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }

}