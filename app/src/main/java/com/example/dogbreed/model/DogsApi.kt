package com.example.dogbreed.model

import io.reactivex.Single
import retrofit2.http.GET

/* Single is an observable that emits data once and finishes*/
interface DogsApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}