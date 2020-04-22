package com.example.dogbreed.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbreed.model.DogBreed
import com.example.dogbreed.model.DogsDatabase
import kotlinx.coroutines.launch

// this class bindings with th DetailFragment.kt in the view folder
class DetailViewModel(application: Application) : BaseViewModel(application) {
    // this variable can't be private, it's called inside the DetailFragment
    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(uuid: Int) {
        launch {
            val dog = DogsDatabase(getApplication()).dogDao().getDog(uuid)
            dogLiveData.value = dog
        }
    }
}