package de.sixbits.pixaclient.e2e

import android.app.Application
import de.sixbits.pixaclient.e2e.di.DaggerTestAppComponent
import de.sixbits.pixaclient.e2e.di.TestAppComponent

class TestMyApplication : Application() {

    val testAppComponent: TestAppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): TestAppComponent {
        return DaggerTestAppComponent.builder()
            .application(this)
            .build()
    }

}