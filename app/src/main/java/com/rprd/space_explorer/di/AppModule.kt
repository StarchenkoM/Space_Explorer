package com.rprd.space_explorer.di

import android.content.Context
import androidx.room.Room
import com.rprd.space_explorer.data.databases.DatabaseRoom
import com.rprd.space_explorer.data.databases.curiositydb.CuriosityDao
import com.rprd.space_explorer.data.databases.dailyphotodb.DailyPhotoDao
import com.rprd.space_explorer.data.databases.opportunitydb.OpportunityDao
import com.rprd.space_explorer.data.repositories.*
import com.rprd.space_explorer.domain.dailyphotousecases.*
import com.rprd.space_explorer.domain.roversusecases.*
import com.rprd.space_explorer.mapers.DailyPhotoMapper
import com.rprd.space_explorer.mapers.RoverMapper
import com.rprd.space_explorer.utils.*
import com.rprd.space_explorer.worker.SpaceWorkManager
import com.rprd.space_explorer.worker.SpaceWorkManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
            @ApplicationContext applicationContext: Context
    ): DatabaseRoom = Room.databaseBuilder(
            applicationContext,
            DatabaseRoom::class.java,
            "space_explorer_database"
    ).build()

    @IoCoroutineScope
    @Provides
    fun providesIoDispatcher(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    //Provide DAO
    @Singleton
    @Provides
    fun provideDailyPhotoDao(appDatabase: DatabaseRoom): DailyPhotoDao = appDatabase.dailyPhotoDao()

    @Singleton
    @Provides
    fun provideCuriosityDao(appDatabase: DatabaseRoom): CuriosityDao = appDatabase.curiosityDao()

    @Singleton
    @Provides
    fun provideOpportunityDao(appDatabase: DatabaseRoom): OpportunityDao =
            appDatabase.opportunityDao()


    //Provide Repositories
    @Provides
    fun provideDailyPhotoRepository(
            dailyPhotoMapper: DailyPhotoMapper,
            dailyPhotoDao: DailyPhotoDao,
            @IoCoroutineScope coroutineScope: CoroutineScope,
    ): DailyPhotoRepository =
            DailyPhotoRepositoryImpl(dailyPhotoMapper, dailyPhotoDao, coroutineScope)

    @Provides
    fun provideCuriosityRepository(
            roverMapper: RoverMapper,
            curiosityDao: CuriosityDao,
    ): CuriosityRepository =
            CuriosityRepositoryImpl(roverMapper, curiosityDao)

    @Provides
    fun provideOpportunityRepository(
            roverMapper: RoverMapper,
            opportunityDao: OpportunityDao,
    ): OpportunityRepository =
            OpportunityRepositoryImpl(roverMapper, opportunityDao)

    //Daily photo Use Cases
    @Provides
    fun providePutDailyPhotoToDBUseCase(repository: DailyPhotoRepository): CheckNewDailyPhotoUseCase =
            CheckNewDailyPhotoUseCaseImpl(repository)

    @Provides
    fun provideGetDailyPhotoApiUseCase(repository: DailyPhotoRepository): GetDailyPhotoUseCase =
            GetDailyPhotoUseCaseImpl(repository)

    @Provides
    fun provideUpdatePhotoFavoriteStatusUseCase(repository: DailyPhotoRepository): UpdatePhotoFavoriteStatusUseCase =
            UpdatePhotoFavoriteStatusUseCaseImpl(repository)

    @Provides
    fun provideGetFavoritePhotosStatusUseCase(repository: DailyPhotoRepository): GetFavoritePhotosStatusUseCase =
            GetFavoritePhotosStatusUseCaseImpl(repository)

    //Rovers Use Cases
    @Provides
    fun provideGetCuriosityPhotosUseCase(repository: CuriosityRepository): GetCuriosityPhotosUseCase =
            GetCuriosityPhotosUseCaseImpl(repository)

    @Provides
    fun provideGetOpportunityPhotosUseCase(repository: OpportunityRepository): GetOpportunityPhotosUseCase =
            GetOpportunityPhotosUseCaseImpl(repository)

    @Provides
    fun provideGetCuriosityPhotosApiUseCase(repository: CuriosityRepository): GetCuriosityPhotosApiUseCase =
            GetCuriosityPhotosApiUseCaseImpl(repository)

    @Provides
    fun provideGetRoverPhotosByNameFromDBUseCase(repository: CuriosityRepository): GetRoverPhotosByNameFromDBUseCase =
            GetRoverPhotosByNameFromDBUseCaseImpl(repository)


    //Image loader Util
    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader =
            ImageLoaderImpl(context)

    //WebView loader Util
    @Provides
    fun provideWebViewLoader(): WebViewLoader = WebViewLoaderImpl()

    //Toast Util
    @Provides
    fun provideToastUtils(@ApplicationContext context: Context): ToastUtil = ToastUtilImpl(context)

    //Time Util
    @Provides
    fun provideTimeUtil(): TimeUtil = TimeUtilImpl()

    @Provides
    fun provideSpaceWorkManager(@ApplicationContext context: Context): SpaceWorkManager = SpaceWorkManagerImpl(context)

}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoCoroutineScope
