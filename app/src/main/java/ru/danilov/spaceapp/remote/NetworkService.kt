package ru.danilov.spaceapp.remote

import ru.danilov.spaceapp.model.Base
import io.reactivex.Single
import ru.danilov.spaceapp.di.component.DaggerApiComponent
import javax.inject.Inject

class NetworkService {

    @Inject
    lateinit var spaceApi: SpaceApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun fetchSpace(): Single<Base> {
        return spaceApi.getSpaceResult()
    }

    companion object {
        val BASE_URL = "https://images-api.nasa.gov"
    }
}