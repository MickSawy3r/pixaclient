package de.sixbits.pixaclient.unit

import android.app.Application
import de.sixbits.pixaclient.unit.di.DaggerTestAppComponent
import de.sixbits.pixaclient.unit.di.TestAppComponent

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