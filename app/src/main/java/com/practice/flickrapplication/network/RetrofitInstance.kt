package com.practice.flickrapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.flickr.com/"

// Retrofit Service Interface
interface FlickrApiService {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getImages(@Query("tags") tags: String): FlickrResponse
}

object RetrofitInstance {
    val api: FlickrApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlickrApiService::class.java)
    }
}