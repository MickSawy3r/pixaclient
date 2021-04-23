package de.sixbits.pixaclient.database

import dagger.Module
import dagger.Provides
import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.database.database.CacheDatabase
import javax.inject.Singleton

@Module
class DatabaseModule