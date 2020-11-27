package ru.danilov.spaceapp.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.danilov.spaceapp.adapter.SpaceAdapter
import ru.danilov.spaceapp.model.Items
import ru.danilov.spaceapp.remote.NetworkService
import ru.danilov.spaceapp.remote.SpaceApi

@Module
class ApiModule {
    @Provides
    fun provideSpaceApi(): SpaceApi {
        return Retrofit.Builder()
            .baseUrl(NetworkService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpaceApi::class.java)
    }

    @Provides
    fun provideItemsList(): ArrayList<Items> {
        return ArrayList()
    }

    @Provides
    fun provideSpaceAdapter(spaces: ArrayList<Items>): SpaceAdapter {
        return SpaceAdapter(spaces)
    }

    @Provides
    fun provideNetworkService(): NetworkService {
        return NetworkService()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}