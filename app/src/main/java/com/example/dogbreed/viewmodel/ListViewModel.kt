package com.example.dogbreed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbreed.model.DogBreed
import com.example.dogbreed.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

// this class bindings with the ListFragment.kt in the view folder
class ListViewModel : ViewModel() {
    private val dogsService = DogsApiService()

    /* method to observe the Single observable method then close it when it's done,
    used to avoid memory leaks, similar to close a listener function when it's finished */
    private val disposable = CompositeDisposable()


    // this variables can't be private, they're called inside ListFragment
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromApi()


        /*val dog1 = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "docile", "")
        val dog2 = DogBreed("2", "Dingo", "15 years", "breedGroup", "bredFor", "docile", "")
        val dog3 = DogBreed("3", "Bull Terrier", "15 years", "breedGroup", "bredFor", "docile", "")

        val dogList = arrayListOf(dog1, dog2, dog3)

        dogs.value = dogList
        loadDogsError.value = false
        loading.value = false*/
    }

    private fun fetchFromApi() {
        loading.value = true
        /* running the api call in the background (another thread)*/
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())  // returning result to the main thread
                /*this method display the API response content with the respective state */
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                    override fun onSuccess(dogsList: List<DogBreed>) {
                        dogs.value = dogsList
                        dogsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    /* ViewModel lifecycle method that keeps track of the observables and finishes when it's done*/
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}