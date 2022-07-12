package com.rprd.space_explorer

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SpaceApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
            Configuration.Builder()
                    .setWorkerFactory(workerFactory)
                    .build()

    override fun onCreate() {
        super.onCreate()
    }
}