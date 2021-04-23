package de.sixbits.pixaclient.main

import dagger.Subcomponent
import de.sixbits.pixaclient.main.ui.MainActivity

@Subcomponent(modules = [
    MainModule::class
])
interface MainComponent {
    @Subcomponent.Factory interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
}