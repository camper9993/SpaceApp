package ru.danilov.spaceapp.viewmodel

import ru.danilov.spaceapp.model.Items
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.danilov.spaceapp.di.component.DaggerApiComponent
import ru.danilov.spaceapp.remote.NetworkService
import javax.inject.Inject

class SpaceViewModel() : ViewModel() {
    @Inject
    lateinit var networkService: NetworkService

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private val itemsList by lazy { MutableLiveData<List<Items>>() }
    val itemsListLD: LiveData<List<Items>>
        get() = itemsList
    private val inProgress by lazy { MutableLiveData<Boolean>() }
    val inProgressLD: LiveData<Boolean>
        get() = inProgress
    private val isError by lazy { MutableLiveData<Boolean>() }
    val isErrorLD: LiveData<Boolean>
        get() = isError



    init {
        DaggerApiComponent.create().inject(this)
        fetchSpace()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun refresh() {
        fetchSpace()
    }

    private fun fetchSpace() {
        compositeDisposable.add( //API call get stored in compositeDisposable
            networkService.fetchSpace() //Makes the call to the endpoint
                .subscribeOn(Schedulers.io()) //Subscribes on a background thread, which is Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //Displays the result on the main thread (UI thread)
                .map { it.collection.items } //Takes the list of vehicles in VehiclesResult pass it on to the next operator
                .subscribeWith(createItemsObserver()) //The glue that connects networkService.fetchVehicle() with createVehicleObserver()
        )
    }

    private fun createItemsObserver(): DisposableSingleObserver<List<Items>> {
        return object : DisposableSingleObserver<List<Items>>() {

            override fun onSuccess(vehicles: List<Items>) {
                inProgress.value = true
                isError.value = false
                itemsList.value = vehicles
                inProgress.value = false

            }

            override fun onError(e: Throwable) {
                inProgress.value = true
                isError.value = true
                Log.e("onError()", "Error: ${e.message}")
                inProgress.value = false
            }
        }
    }
}