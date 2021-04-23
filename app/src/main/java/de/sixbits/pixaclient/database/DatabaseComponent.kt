package de.sixbits.pixaclient.database

import dagger.Subcomponent

@Subcomponent(modules = [
    DatabaseModule::class
])
interface DatabaseComponent {
    @Subcomponent.Factory interface Factory {
        fun create(): DatabaseComponent
    }
}