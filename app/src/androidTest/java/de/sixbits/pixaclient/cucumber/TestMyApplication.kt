package de.sixbits.pixaclient.cucumber

import android.app.Application
import de.sixbits.pixaclient.cucumber.di.TestAppComponent
import de.sixbits.pixaclient.cucumber.di.DaggerTestAppComponent

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