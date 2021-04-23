package de.sixbits.pixaclient.di

import android.content.Context
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
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun networkComponent(): NetworkComponent.Factory
    fun mainComponent(): MainComponent.Factory
    fun databaseComponent(): DatabaseComponent.Factory
}