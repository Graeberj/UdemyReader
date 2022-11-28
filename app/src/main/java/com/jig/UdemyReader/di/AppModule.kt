package com.jig.UdemyReader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.jig.UdemyReader.network.BooksApi
import com.jig.UdemyReader.repository.BookRepository
import com.jig.UdemyReader.repository.FireRepository
import com.jig.UdemyReader.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFireBookRepository()
    = FireRepository(queryBook = FirebaseFirestore.getInstance()
        .collection("books")
    )

    @Singleton
    @Provides
    fun providesBookRepository(api: BooksApi) = BookRepository(api)

    @Singleton
    @Provides
    fun provideBookApi(): BooksApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}