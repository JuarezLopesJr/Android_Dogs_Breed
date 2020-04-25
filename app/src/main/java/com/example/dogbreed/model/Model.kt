package com.example.dogbreed.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*  @SerializedName value come from the respective field in the JSON response from the API
* if the name of the variable e.g val name:String? is equal to the field in the JSON, i don't need to pass
*  @SerializedName
* */

@Entity // annotating that the class can be put in a DB
data class DogBreed(
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val dogBreed: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan: String?,

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGroup: String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor: String?,

    @ColumnInfo(name = "temperament")
    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val imageUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid = 0
}

data class DogPalette(var color: Int)

data class SMSInfo(
    var to: String,
    var text: String,
    var imageUrl: String?
)