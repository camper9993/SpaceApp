package ru.danilov.spaceapp.remote

import ru.danilov.spaceapp.model.Base
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceApi {
    @GET("/search?media_type=image")
    fun getSpaceResult(): Single<Base>
}