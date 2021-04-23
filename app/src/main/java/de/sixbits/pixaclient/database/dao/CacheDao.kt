package de.sixbits.pixaclient.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.sixbits.pixaclient.database.entities.ImageEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CacheDao {
    @Query("SELECT * from image_entity")
    fun getAll(): Observable<List<ImageEntity>>

    @Query("SELECT * from image_entity where id = :id")
    fun getById(id: Int): Observable<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageEntity): Completable
}