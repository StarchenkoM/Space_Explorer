package com.rprd.space_explorer.data.databases.opportunitydb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OpportunityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPhotos(photoDBList: List<OpportunityPhotoDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photoDB: OpportunityPhotoDBEntity)

    @Query("delete from opportunity_photos")
    suspend fun deleteAllData()

    @Query("select * from opportunity_photos")
    fun getAllData(): List<OpportunityPhotoDBEntity>

    @Query("select * from opportunity_photos where roverName ==:roverName")
    fun getRoverData(roverName: String): List<OpportunityPhotoDBEntity>

    @Query("select * from opportunity_photos where sol ==:sol")
    fun getRoverDataNameSol(sol: Int): List<OpportunityPhotoDBEntity>

    @Query("select * from opportunity_photos")
    fun getAllDataAsList(): List<OpportunityPhotoDBEntity>

}
