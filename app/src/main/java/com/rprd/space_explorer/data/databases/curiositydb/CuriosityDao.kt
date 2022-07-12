package com.rprd.space_explorer.data.databases.curiositydb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface CuriosityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPhotos(photoDBList: List<CuriosityPhotoDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photoDB: CuriosityPhotoDBEntity)

    @Query("delete from curiosity_photos")
    suspend fun deleteAllData()

    @Query("select * from curiosity_photos")
    fun getAllData(): LiveData<List<CuriosityPhotoDBEntity>>

    @Query("select * from curiosity_photos")
    fun getRoverData(): List<CuriosityPhotoDBEntity>

    @Query("select * from curiosity_photos where sol ==:sol")
    fun getRoverPhotoByDate(sol: Int): List<CuriosityPhotoDBEntity>

    @Query("select * from curiosity_photos")
    fun getAllDataAsList(): List<CuriosityPhotoDBEntity>

    @Query("select * from curiosity_photos where roverName ==:roverName")
    fun getRoverDataRX(roverName: String): Flowable<List<CuriosityPhotoDBEntity>>

}