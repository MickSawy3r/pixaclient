package de.sixbits.pixaclient.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import de.sixbits.pixaclient.main.repository.MainRepository
import de.sixbits.pixaclient.main.view_model.DetailsViewModel
import de.sixbits.pixaclient.main.view_model.MainViewModel
import de.sixbits.pixaclient.view_model.ViewModelKey
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class MainModule {
    // Providing here!

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun detailsViewModel(viewModel: DetailsViewModel): ViewModel
}
