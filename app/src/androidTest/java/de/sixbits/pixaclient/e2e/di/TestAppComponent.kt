package de.sixbits.pixaclient.e2e.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import de.sixbits.pixaclient.database.DatabaseComponent
import de.sixbits.pixaclient.e2e.TestMyApplication
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.network.NetworkComponent
import de.sixbits.pixaclient.view_model.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestAppModule::class,
        ViewModelModule::class
    ]
)
interface TestAppComponent : AndroidInjector<TestMyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): TestAppComponent
    }

    fun networkComponent(): NetworkComponent.Factory
    fun mainComponent(): MainComponent.Factory
    fun databaseComponent(): DatabaseComponent.Factory
}



