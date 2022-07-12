package com.rprd.space_explorer.data.databases.dailyphotodb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DailyPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photo: DailyPhotoDBEntity)

    @Query("delete from daily_photos")
    suspend fun deleteAllData()

    @Query("select * from daily_photos")
    fun getAllPhotos(): LiveData<List<DailyPhotoDBEntity>>

    @Query("select * from daily_photos where pictureDate ==:pictureDate")
    fun getDailyPhotoItem(pictureDate: String): DailyPhotoDBEntity?

    @Query("select * from daily_photos")
    fun getAllDataAsList(): List<DailyPhotoDBEntity>

    @Query("UPDATE daily_photos SET isFavorite=:isFavorite WHERE pictureDate = :date")
    fun updateFavoriteField(date: String, isFavorite: Boolean): Int

    @Query("select * from daily_photos where isFavorite ==:isFavorite")
    fun getFavoritePhotos(isFavorite: Boolean): List<DailyPhotoDBEntity>
}