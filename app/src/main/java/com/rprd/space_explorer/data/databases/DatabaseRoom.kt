package com.rprd.space_explorer.data.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rprd.space_explorer.data.databases.curiositydb.CuriosityDao
import com.rprd.space_explorer.data.databases.curiositydb.CuriosityPhotoDBEntity
import com.rprd.space_explorer.data.databases.dailyphotodb.DailyPhotoDBEntity
import com.rprd.space_explorer.data.databases.dailyphotodb.DailyPhotoDao
import com.rprd.space_explorer.data.databases.opportunitydb.OpportunityDao
import com.rprd.space_explorer.data.databases.opportunitydb.OpportunityPhotoDBEntity
import com.rprd.space_explorer.utils.ROVER_DATABASE_VERSION

@Database(
        entities = [DailyPhotoDBEntity::class, CuriosityPhotoDBEntity::class, OpportunityPhotoDBEntity::class],
        version = ROVER_DATABASE_VERSION
)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun dailyPhotoDao(): DailyPhotoDao
    abstract fun curiosityDao(): CuriosityDao
    abstract fun opportunityDao(): OpportunityDao
}