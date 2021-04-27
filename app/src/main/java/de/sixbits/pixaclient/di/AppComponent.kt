package de.sixbits.pixaclient.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.database.DatabaseComponent
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.network.NetworkComponent
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun networkComponent(): NetworkComponent.Factory
    fun mainComponent(): MainComponent.Factory
    fun databaseComponent(): DatabaseComponent.Factory
}