package de.sixbits.pixaclient

import android.app.Application
import de.sixbits.pixaclient.di.AppComponent
import de.sixbits.pixaclient.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent
            .factory()
            .create(applicationContext)
    }
}