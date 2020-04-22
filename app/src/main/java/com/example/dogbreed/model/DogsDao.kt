package com.example.dogbreed.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Data Access Object
@Dao
interface DogsDao {
    // using suspend, Coroutines syntax
    @Insert
    suspend fun insertAll(vararg dog: DogBreed): List<Long> // this will be the uuid (Primary Key)

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int): DogBreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs(): Unit

}