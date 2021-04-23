package de.sixbits.pixaclient.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.sixbits.pixaclient.database.entities.ImageEntity

@Dao
interface CacheDao {
    @Query("SELECT * from image_entity")
    fun getAll(): List<ImageEntity>

    @Query("SELECT * from image_entity where id = :id")
    fun getById(id: Int): ImageEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageEntity)
}