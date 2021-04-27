package de.sixbits.pixaclient.e2e

import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.e2e.di.DaggerTestAppComponent
import de.sixbits.pixaclient.e2e.di.TestAppComponent

class TestMyApplication : MyApplication() {

    @Override
    fun initializeComponent(): TestAppComponent {
        return DaggerTestAppComponent.builder()
            .application(this)
            .build()
    }

}