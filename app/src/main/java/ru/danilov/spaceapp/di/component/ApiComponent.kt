package ru.danilov.spaceapp.di.component

import dagger.Component
import ru.danilov.spaceapp.ui.MainActivity
import ru.danilov.spaceapp.di.module.ApiModule
import ru.danilov.spaceapp.remote.NetworkService
import ru.danilov.spaceapp.viewmodel.SpaceViewModel

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(networkService: NetworkService)

    fun inject(spaceViewModel: SpaceViewModel)

    fun inject(mainActivity: MainActivity)
}