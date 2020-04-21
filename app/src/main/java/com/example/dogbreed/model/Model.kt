package com.example.dogbreed.model

import com.google.gson.annotations.SerializedName

/*  @SerializedName value come from the respective field in the JSON response from the API
* if the name of the variable e.g val name:String? is equal to the field in the JSON, i don't need to pass
*  @SerializedName
* */
data class DogBreed(
    @SerializedName("id")
    val breedId: String?,

    @SerializedName("name")
    val dogBreed: String?,

    @SerializedName("life_span")
    val lifeSpan: String?,

    @SerializedName("breed_group")
    val breedGroup: String?,

    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @SerializedName("url")
    val imageUrl: String?
)