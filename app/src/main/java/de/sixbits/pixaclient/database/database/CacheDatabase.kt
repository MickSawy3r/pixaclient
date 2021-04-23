package de.sixbits.pixaclient.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.sixbits.pixaclient.database.converters.DateConverter
import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.database.entities.ImageEntity

@Database(
    entities = [ImageEntity::class],
    version = 1,
)
@TypeConverters(DateConverter::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}