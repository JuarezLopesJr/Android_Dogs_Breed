package com.example.dogbreed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/* Using AndroidViewModel instead of ViewModel, to pass the context of the application to instantiate/access
 the DB, and not the Activity context from the ViewModel */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job() // background thread

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // when job is done, return to the Main thread

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}