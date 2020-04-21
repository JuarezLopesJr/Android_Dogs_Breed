package com.example.dogbreed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbreed.model.DogBreed

// this class bindings with th DetailFragment.kt in the view folder
class DetailViewModel : ViewModel() {
    // this variable can't be private, it's called inside the DetailFragment
    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch() {
        val dog = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "docile", "")
        dogLiveData.value = dog
    }
}