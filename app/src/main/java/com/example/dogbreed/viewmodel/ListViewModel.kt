package com.example.dogbreed.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.dogbreed.model.DogBreed
import com.example.dogbreed.model.DogsApiService
import com.example.dogbreed.model.DogsDatabase
import com.example.dogbreed.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/* this class binds with the ListFragment.kt in the view folder
  it's also getting the Coroutines methods defined in the BaseViewModel to access the DB
*/
class ListViewModel(application: Application) : BaseViewModel(application) {
    private var prefsHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L // 5 min definition in nano seconds

    private val dogsService = DogsApiService()

    /* method to observe the Single observable method then close it when it's done,
    used to avoid memory leaks, similar to close a listener function when it's finished */
    private val disposable = CompositeDisposable()


    // this variables can't be private, they're called inside ListFragment
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    // called by the view to retrieve data
    fun refresh() {
        val updateTime = prefsHelper.getUpdateTime()

        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromApi()
        }


        /*val dog1 = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "docile", "")
        val dog2 = DogBreed("2", "Dingo", "15 years", "breedGroup", "bredFor", "docile", "")
        val dog3 = DogBreed("3", "Bull Terrier", "15 years", "breedGroup", "bredFor", "docile", "")

        val dogList = arrayListOf(dog1, dog2, dog3)

        dogs.value = dogList
        loadDogsError.value = false
        loading.value = false*/
    }

    // will refresh the data from the endpoint in the swipe to refresh user action
    fun refreshBypassCache() {
        fetchFromApi()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs = DogsDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "Dogs retrieved from database", Toast.LENGTH_SHORT).show()
        }
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
                        storeDogsLocally(dogsList)
                        Toast.makeText(getApplication(), "Dogs retrieved from API", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun dogsRetrieved(dogsList: List<DogBreed>) {
        dogs.value = dogsList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<DogBreed>) {
        launch {
            val dao = DogsDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray()) // * means extend/expand operator
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(list)
        }
        prefsHelper.saveUpdateTime(System.nanoTime()) // getting the timestamp for the updated DB
    }

    /* ViewModel lifecycle method that keeps track of the observables and finishes when it's done*/
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}